package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd02.managers.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtModelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Klasa menadżera do obsługi operacji związanych z modelami jachtów.
 */
@Stateful(name = "YachtModelManager")
@Named("YachtModelManager")
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtModelManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private YachtModelFacade yachtModelFacade;

    /**
     * Metoda, służy do dodawania nowych modeli jachtów do bazy danych przez menadżera.
     *
     * @param yachtModel obiekt encji modelu jachtu do dodania
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addYachtModel(YachtModel yachtModel) throws AppBaseException {
        try {
            if (yachtModelFacade.existByModel(yachtModel.getModel())) {
                throw ValueNotUniqueException.createYachtModelNotUniqueException(yachtModel);
            }
            YachtModel yachtModelToAdd = new YachtModel(yachtModel.getManufacturer(), yachtModel.getModel(), yachtModel.getCapacity(),
                    yachtModel.getGeneralDescription(), yachtModel.getBasicPrice());
            yachtModelFacade.create(yachtModelToAdd);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda która zwraca listę wszystkich modeli jachtów.
     *
     * @return lista modeli jachtów
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<YachtModel> getAllYachtModels() {
        return yachtModelFacade.findAll();
    }

    /**
     * Metoda która zwraca model jachtu o podanym id.
     *
     * @param yachtModelId id modelu jachtu
     * @return model jachtu
     * @throws AppBaseException wyjątek aplikacyjny jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public YachtModel getYachtModelById(Long yachtModelId) throws AppBaseException {
        return yachtModelFacade.find(yachtModelId).orElseThrow((AppNotFoundException::yachtModelNotFoundException));
    }

    /**
     * Metoda, która zapisuje wprowadzone przez managera zmiany w modelu jachtu.
     *
     * @param yachtModelToEdit edytowany model jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editYachtModel(YachtModel yachtModelToEdit) throws AppBaseException {
        try {
            if (!yachtModelToEdit.isActive()) {
                throw EntityNotActiveException.createYachtModelNotActiveException(yachtModelToEdit);
            }
            yachtModelFacade.edit(yachtModelToEdit);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }

    /**
     * Metoda, która deaktywuje model jachtu o podanym id.
     *
     * @param yachtModelId id modelu jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deactivateYachtModel(Long yachtModelId) throws AppBaseException {
        try {
            YachtModel yachtModelToDeactivate = yachtModelFacade.find(yachtModelId).orElseThrow(AppNotFoundException::yachtModelNotFoundException);
            yachtModelToDeactivate.setActive(false);
            yachtModelFacade.edit(yachtModelToDeactivate);
        } catch (EJBTransactionRolledbackException e) {
            throw AppEJBTransactionRolledbackException.createAppEJBTransactionRolledbackException(e);
        }
    }
}
