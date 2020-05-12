package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtFacade;
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
public class YachtManager {
    @Inject
    private YachtFacade yachtFacade;
    @Inject
    private YachtModelFacade yachtModelFacade;

    public void addYacht(Yacht yacht) {
        yachtFacade.create(yacht);
    }

    /**
     * Metoda, która zwraca wszystkie jachty
     *
     * @return lista jachtów
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getAllYachts")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Yacht> getAllYachts(){
        return yachtFacade.findAll();
    }

    /**
     * Metoda, która zwraca yacht o podanym id.
     *
     * @param yachtId id jachtu.
     * @return yacht dto
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getYachtById")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Yacht getYachtById(Long yachtId) throws AppBaseException {
        //TODO poprawic na odpowiedni wyjątek
        return yachtFacade.find(yachtId).orElseThrow(() -> new AppBaseException("nie ma tego jachtu"));
    }

    public void editYacht(Long yachtId, Yacht yachtToEdit) throws AppBaseException {
        //yachtToEdit.setId(yachtId);
        yachtFacade.edit(yachtToEdit);
    }

    public void deactivateYacht(Long yachtId) throws AppBaseException{
        Yacht yachtToDeactivate = getYachtById(yachtId);
        yachtToDeactivate.setActive(false);
        yachtFacade.edit(yachtToDeactivate);
    }
}
