package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import org.eclipse.persistence.annotations.ReadOnly;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.ValueNotUniqueException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtModelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class YachtModelManager {
    @Inject
    private YachtModelFacade yachtModelFacade;

    /**
     * Metoda, służy do dodawania nowych modeli jachtów do bazy danych przez menadżera
     *
     * @param yachtModel obiekt encji modelu jachtu do dodania.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addYachtModel")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addYachtModel(YachtModel yachtModel) throws AppBaseException {
        if(yachtModelFacade.existByModel(yachtModel.getModel())){
            throw ValueNotUniqueException.createYachtModelNotUniqueException(yachtModel);
        }
        yachtModelFacade.create(yachtModel);
    }

    /**
     * Metoda która zwraca listę wszystkich modeli jachtów
     * @return lista modeli jachtów
     */
    @RolesAllowed("getAllYachtModels")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<YachtModel> getAllYachtModels() {
        return yachtModelFacade.findAll();
    }

    /**
     * Metoda która zwraca model jachtu o podanym id
     * @param yachtModelId id modelu jachtu
     * @return model jachtu
     * @throws AppBaseException wyjątek aplikacyjny jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getYachtModelById")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public YachtModel getYachtModelById(Long yachtModelId) throws AppBaseException {
        //TODO poprawic na odpowiedni wyjątek
        return yachtModelFacade.find(yachtModelId).orElseThrow(() -> new AppBaseException("nie ma tego modelu"));
    }

    /**
     * Metoda, która zapisuje wprowadzone przez managera zmiany w modelu jachtu
     * @param yachtModelToEdit edytowany model jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editYachtModel")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editYachtModel(YachtModel yachtModelToEdit) throws AppBaseException {
        yachtModelFacade.edit(yachtModelToEdit);
    }

    /**
     * Metoda, która deaktywuje model jachtu o podanym id.
     * @param yachtModelId id modelu jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("deactivateYachtModel")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deactivateYachtModel(Long yachtModelId) throws AppBaseException{
        YachtModel yachtModelToDeactivate = yachtModelFacade.find(yachtModelId).orElseThrow(AppNotFoundException::createYachtNotFoundException);
        yachtModelToDeactivate.setActive(false);
        yachtModelFacade.edit(yachtModelToDeactivate);
    }
}
