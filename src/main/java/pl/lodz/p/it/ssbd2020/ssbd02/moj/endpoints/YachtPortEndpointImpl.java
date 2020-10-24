package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtByPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppEJBTransactionRolledbackException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.RepeatedRollBackException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.YachtPortManager;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementacja interfejsu YachtPortEndpoint.
 */
@Stateful(name = "YachtPortEndpointImpl")
@Named("YachtPortEndpointImpl")
@Interceptors(LoggerInterceptor.class)
public class YachtPortEndpointImpl implements Serializable, YachtPortEndpoint {
    PropertyReader propertyReader = new PropertyReader();
    Integer METHOD_INVOCATION_LIMIT;
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Inject
    private @Named("YachtPortManager") YachtPortManager yachtPortManager;

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        METHOD_INVOCATION_LIMIT = Integer.parseInt(propertyReader.getProperty("config", "rollback.invocation.limit"));
    }

    /**
     * Metoda pobierająca wszystki jachty przypisane do danego portu.
     *
     * @param portId identyfikator danego portu
     * @return lista YachtDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getAllYachtsByPort")
    public List<YachtByPortDto> getAllYachtsByPort(Long portId) throws AppBaseException {
        return ObjectMapperUtils.mapAll(yachtPortManager.getAllYachtsByPort(portId), YachtByPortDto.class);
    }

    /**
     * Metoda przypisująca jacht do portu.
     *
     * @param portId  identyfikator danego portu
     * @param yachtId identyfikator danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("assignYachtToPort")
    public void assignYachtToPort(Long portId, Long yachtId) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                yachtPortManager.assignYachtToPort(portId, yachtId);
                rollback = yachtPortManager.isLastTransactionRollback();
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
     * Metoda odpisująca jacht z portu.
     *
     * @param yachtId identyfikator danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("retractYachtFromPort")
    public void retractYachtFromPort(Long yachtId) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                yachtPortManager.retractYachtFromPort(yachtId);
                rollback = yachtPortManager.isLastTransactionRollback();
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
