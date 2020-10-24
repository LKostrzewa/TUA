package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Opinion;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd02.managers.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.OpinionFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.RentalFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Klasa menadżera do obsługi operacji związanych z opiniami.
 */
@Stateful(name = "OpinionManager")
@Named("OpinionManager")
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class OpinionManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private @Named("RentalFacade") RentalFacade rentalFacade;
    @Inject
    private @Named("OpinionFacade") OpinionFacade opinionFacade;
    @Inject
    private @Named("YachtFacade") YachtFacade yachtFacade;

    /**
     * Metoda, która dodaje nową opinię.
     *
     * @param opinion           encja z nową opinią do dodania
     * @param rentalBusinessKey klucz biznesowy wypożyczenia, dla którego chcemy dodać opinię
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addOpinion")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addOpinion(Opinion opinion, String rentalBusinessKey) throws AppBaseException {
        try {
            PropertyReader propertyReader = new PropertyReader();
            Rental rental = rentalFacade.findByBusinessKey(UUID.fromString(rentalBusinessKey))
                    .orElseThrow(AppNotFoundException::createRentalNotFoundException);
            if (!rental.getRentalStatus().getName().equals(propertyReader.getProperty("config", "FINISHED_STATUS"))) {
                throw RentalNotFinishedException.createRentalNotFinishedException(rental);
            }
            if (rental.getOpinion() != null) {
                throw OpinionAlreadyExistsException.createOpinionAlreadyExistsException(rental);
            }
            Opinion newOpinion = new Opinion(opinion.getRating(), opinion.getComment(), new Date(), rental);
            opinionFacade.create(newOpinion);
            calculateAvgRating(newOpinion.getRental().getYacht().getId());
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda zwracająca opinię na podstawie przekazanego klucza biznesowego.
     *
     * @param rentalBusinessKey klucz biznesowy opinii
     * @return encja opinii
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getOpinionByBusinessKey")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Opinion getOpinionByRentalBusinessKey(String rentalBusinessKey) throws AppBaseException {
        return opinionFacade.getOpinionByRentalBusinessKey(rentalBusinessKey);
    }

    /**
     * Metoda służąca do zapisu nowej wersji opinii.
     *
     * @param opinionEditEntity encja z danymi edytowanej opinii
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editOpinion")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editOpinion(Opinion opinionEditEntity) throws AppBaseException {
        try {
            opinionEditEntity.setEdited(true);
            opinionFacade.edit(opinionEditEntity);
            calculateAvgRating(opinionEditEntity.getRental().getYacht().getId());
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda do wyliczenia nowej średniej ceny dla yachtu o podanym identyfikatorze.
     *
     * @param yachtId identyfikator jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    private void calculateAvgRating(Long yachtId) throws AppBaseException {
        try {
            Yacht yacht = yachtFacade.find(yachtId).orElseThrow(AppNotFoundException::createYachtNotFoundException);
            List<Opinion> opinionList = opinionFacade.getAllOpinionsByYacht(yachtId);
            yacht.setAvgRating(BigDecimal
                    .valueOf(opinionList
                            .stream()
                            .mapToDouble(Opinion::getRating)
                            .average()
                            .orElse(Double.NaN))
                    .setScale(2, RoundingMode.HALF_UP));
            yachtFacade.edit(yacht);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }
}
