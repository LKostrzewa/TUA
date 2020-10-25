package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;


import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppEJBTransactionRolledbackException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.RepeatedRollBackException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.EditYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.NewYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtListDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.YachtManager;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementacja interfejsu YachtEndpoint.
 */
@Stateful(name = "YachtEndpointImpl")
@Named("YachtEndpointImpl")
@Interceptors(LoggerInterceptor.class)
public class YachtEndpointImpl implements Serializable, YachtEndpoint {
    PropertyReader propertyReader = new PropertyReader();
    Integer METHOD_INVOCATION_LIMIT;
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Inject
    private YachtManager yachtManager;
    private Yacht yachtEditEntity;

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        METHOD_INVOCATION_LIMIT = Integer.parseInt(propertyReader.getProperty("config", "rollback.invocation.limit"));
    }

    /**
     * Metoda, służy do dodawania nowych jachtów do bazy danych przez menadżera.
     *
     * @param newYachtDto obiekt DTO z danymi nowego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public void addYacht(NewYachtDto newYachtDto) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                Yacht yacht = new Yacht(newYachtDto.getName(), newYachtDto.getProductionYear(), newYachtDto.getPriceMultiplier(), newYachtDto.getEquipment(), null);
                yachtManager.addYacht(yacht, newYachtDto.getYachtModelId());
                rollback = yachtManager.isLastTransactionRollback();
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
     * Metoda, która zwraca wszystkie jachty.
     *
     * @return lista jachtów
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public List<YachtListDto> getAllYachts() {
        List<Yacht> yachtList = yachtManager.getAllYachts();
        List<YachtListDto> yachtListDtoList = new ArrayList<>();
        for (Yacht yacht : yachtList) {
            yachtListDtoList.add(new YachtListDto(yacht.getId(), yacht.getName(), yacht.getYachtModel().getModel(),
                    (yacht.getCurrentPort() != null) ? yacht.getCurrentPort().getName() : null,
                    (yacht.getAvgRating() != null) ? yacht.getAvgRating().floatValue() : null
                    , yacht.isActive()));
        }
        return yachtListDtoList;
    }

    /**
     * Metoda, która zwraca yacht o podanym id.
     *
     * @param yachtId id jachtu.
     * @return YachtDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public YachtDto getYachtById(Long yachtId) throws AppBaseException {
        Yacht yacht = yachtManager.getYachtById(yachtId);
        return ObjectMapperUtils.map(yacht, YachtDto.class);
    }

    /**
     * Metoda, która zwraca yacht do edycji o podanym id.
     *
     * @param yachtId id jachtu.
     * @return EditYachtDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public EditYachtDto getEditYachtDtoById(Long yachtId) throws AppBaseException {
        this.yachtEditEntity = yachtManager.getYachtById(yachtId);
        return ObjectMapperUtils.map(this.yachtEditEntity, EditYachtDto.class);
    }

    /**
     * Metoda, która zapisuje wprowadzone przez managera zmiany w jachcie.
     *
     * @param editYachtDto id jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public void editYacht(EditYachtDto editYachtDto) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                boolean nameChanged = true;
                if (yachtEditEntity.getName().equals(editYachtDto.getName())) {
                    nameChanged = false;
                }
                yachtEditEntity.setName(editYachtDto.getName());
                yachtEditEntity.setPriceMultiplier(editYachtDto.getPriceMultiplier());
                yachtEditEntity.setEquipment(editYachtDto.getEquipment());
                yachtManager.editYacht(this.yachtEditEntity, nameChanged);
                rollback = yachtManager.isLastTransactionRollback();
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
     * Metoda, która deaktywuje jacht o podanym id.
     *
     * @param yachtId id jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public void deactivateYacht(Long yachtId) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                yachtManager.deactivateYacht(yachtId);
                rollback = yachtManager.isLastTransactionRollback();
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
