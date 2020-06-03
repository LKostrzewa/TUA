package pl.lodz.p.it.ssbd2020.ssbd02.managers;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Abstrakcyjna klasa menadżera implementująca metody wymagane dla interfejsu SessionSynchronization.
 */
abstract public class AbstractManager {
    protected final static Logger LOGGER = Logger.getGlobal();
    @Resource
    private SessionContext sessionContext;
    private String transactionId;
    private boolean lastTransactionRollback;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public boolean isLastTransactionRollback() {
        return lastTransactionRollback;
    }

    /**
     * Metoda uruchomiana po rozpoczęciu transakcji.
     * Zapisuje do dziennika zdarzeń informację o rozpoczęciu transakcji
     */
    public void afterBegin() {
        transactionId = Long.toString(System.currentTimeMillis())
                + ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        LOGGER.log(Level.INFO, "Transakcja TXid={0} rozpoczęta w {1}, tożsamość {2}",
                new Object[]{transactionId, this.getClass().getName(), sessionContext.getCallerPrincipal().getName()});
    }

    /**
     * Metoda uruchomiana przed zakończeniem transakcji.
     * Zapisuje do dziennika zdarzeń informację o tym, że transakcja zaraz się zakończy
     */
    public void beforeCompletion() {
        LOGGER.log(Level.INFO, "Transakcja TXid={0} przed zatwierdzeniem w {1}, tożsamość {2}",
                new Object[]{transactionId, this.getClass().getName(), sessionContext.getCallerPrincipal().getName()});
    }

    /**
     * Metoda uruchomiana po zakończeniu transakcji.
     * Zapisuje do dziennika zdarzeń informację o zakończeniu transakcji,
     * a także statusie z jakim się zakończyła:
     * Commited - zatwierdzenie
     * Rollback - odwołanie
     */
    public void afterCompletion(boolean commited) {
        lastTransactionRollback = !commited;
        LOGGER.log(Level.INFO, "Transakcja TXid={0} zakończona w {1}, poprzez {3}, tożsamość {2}",
                new Object[]{transactionId, this.getClass().getName(),
                        sessionContext.getCallerPrincipal().getName(), commited ? "ZATWIERDZENIE" : "ODWOŁANIE"});
    }
}


