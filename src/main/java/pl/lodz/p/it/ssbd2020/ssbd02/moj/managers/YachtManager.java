package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.EntityNotActiveException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.ValueNotUniqueException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.NewYachtDto;
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

    /**
     * Metoda, służy do dodawania nowych jachtów do bazy danych przez menadżera
     *
     * @param newYachtDto obiekt DTO z danymi nowego jachtu.
     * @param yachtModelId id modelu jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addYacht")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addYacht(Yacht newYachtDto, Long yachtModelId) throws AppBaseException {
        // TODO zmienic wyjatek na yacht model not find
        YachtModel yachtModel = yachtModelFacade.find(yachtModelId).orElseThrow(AppNotFoundException::createUserNotFoundException);
        Yacht newYacht = new Yacht(newYachtDto.getName(),newYachtDto.getProductionYear(),newYachtDto.getPriceMultiplier(),newYachtDto.getEquipment(), yachtModel);

        if(!yachtModel.isActive()){
            throw EntityNotActiveException.createYachtModelNotActiveException(yachtModel);
        }

        if (yachtFacade.existByName(newYacht.getName())) {
            throw ValueNotUniqueException.createYachtNameNotUniqueException(newYacht);
        }

        yachtFacade.create(newYacht);
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
    @RolesAllowed({"getYachtById","getEditYachtDtoById"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Yacht getYachtById(Long yachtId) throws AppBaseException {
        return yachtFacade.find(yachtId).orElseThrow(AppNotFoundException::createYachtNotFoundException);
    }

    /**
     * Metoda, która zapisuje wprowadzone przez managera zmiany w jachcie
     *
     * @param yachtToEdit edytowany jacht.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editYacht")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editYacht(Yacht yachtToEdit) throws AppBaseException {
        if (yachtFacade.existByName(yachtToEdit.getName())) {
            throw ValueNotUniqueException.createYachtNameNotUniqueException(yachtToEdit);
        }
        yachtFacade.edit(yachtToEdit);
    }

    /**
     * Metoda, która deaktywuje jacht o podanym id.
     *
     * @param yachtId id jachtu.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("deactivateYacht")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void deactivateYacht(Long yachtId) throws AppBaseException{
        Yacht yachtToDeactivate = yachtFacade.find(yachtId).orElseThrow(AppNotFoundException::createYachtNotFoundException);
        yachtToDeactivate.setActive(false);
        yachtFacade.edit(yachtToDeactivate);
    }
}
