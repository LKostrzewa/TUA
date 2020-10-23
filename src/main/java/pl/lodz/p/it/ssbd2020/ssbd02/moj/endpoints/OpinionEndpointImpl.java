package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Opinion;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppEJBTransactionRolledbackException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.RepeatedRollBackException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.EditOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.NewOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.OpinionManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementacja interfejsu OpinionEndpoint.
 */
@Stateful(name = "OpinionEndpointImpl")
@Named("OpinionEndpointImpl")
@Interceptors(LoggerInterceptor.class)
public class OpinionEndpointImpl implements Serializable, OpinionEndpoint {
    PropertyReader propertyReader = new PropertyReader();
    Integer METHOD_INVOCATION_LIMIT;
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Inject
    private OpinionManager opinionManager;
    private Opinion opinionEditEntity;

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        METHOD_INVOCATION_LIMIT = Integer.parseInt(propertyReader.getProperty("config", "rollback.invocation.limit"));
    }

    /**
     * Metoda, która dodaje nową opinię.
     *
     * @param newOpinionDto obiekt DTO z danymi nowej opinii.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addOpinion")
    public void addOpinion(NewOpinionDto newOpinionDto, String rentalBusinessKey) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                Opinion opinion = new Opinion(newOpinionDto.getRating(), newOpinionDto.getComment(), null, null);
                opinionManager.addOpinion(opinion, rentalBusinessKey);
                rollback = opinionManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }

        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }

    /**
     * Metoda zwracająca opinię do edycji na podstawie przekazanego klucza biznesowego.
     *
     * @param rentalBusinessKey klucz biznesowy opinii
     * @return opinia do edycji
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getOpinionByBusinessKey")
    public EditOpinionDto getOpinionByRentalBusinessKey(String rentalBusinessKey) throws AppBaseException {
        this.opinionEditEntity = opinionManager.getOpinionByRentalBusinessKey(rentalBusinessKey);
        return ObjectMapperUtils.map(opinionEditEntity, EditOpinionDto.class);
    }

    /**
     * Metoda służąca do zapisu nowej wersji opinii.
     *
     * @param editOpinionDto obiekt DTO z danymi edytowanej opinii.
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editOpinion")
    public void editOpinion(EditOpinionDto editOpinionDto) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                opinionEditEntity.setRating(editOpinionDto.getRating());
                opinionEditEntity.setComment(editOpinionDto.getComment());
                opinionManager.editOpinion(opinionEditEntity);
                rollback = opinionManager.isLastTransactionRollback();
            } catch (AppEJBTransactionRolledbackException ex) {
                logger.log(Level.WARNING, "Exception EJBTransactionRolledback");
                rollback = true;
            } finally {
                if (methodInvocationCounter > 0)
                    logger.log(Level.WARNING, "Transaction repeated " + methodInvocationCounter + " times");
                methodInvocationCounter++;
            }

        } while (rollback && methodInvocationCounter < METHOD_INVOCATION_LIMIT);

        if (methodInvocationCounter == METHOD_INVOCATION_LIMIT) {
            throw RepeatedRollBackException.createRepeatedRollBackException();
        }
    }
}
