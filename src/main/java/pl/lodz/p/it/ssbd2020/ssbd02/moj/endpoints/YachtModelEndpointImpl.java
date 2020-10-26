package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppEJBTransactionRolledbackException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.RepeatedRollBackException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.EditYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.ListYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.NewYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.YachtModelDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.YachtModelManager;
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
 * Implementacja interfejsu YachtModelEndpoint.
 */
@Stateful(name = "YachtModelEndpointImpl")
@Named("YachtModelEndpointImpl")
@Interceptors(LoggerInterceptor.class)
public class YachtModelEndpointImpl implements Serializable, YachtModelEndpoint {
    PropertyReader propertyReader = new PropertyReader();
    Integer METHOD_INVOCATION_LIMIT;
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Inject
    private YachtModelManager yachtModelManager;
    private YachtModel yachtModelEditEntity;

    /**
     * Metoda inicjalizująca komponent.
     */
    @PostConstruct
    public void init() {
        METHOD_INVOCATION_LIMIT = Integer.parseInt(propertyReader.getProperty("config", "rollback.invocation.limit"));
    }

    /**
     * Metoda, służy do dodawania nowych modeli jachtów do bazy danych przez menadżera.
     *
     * @param newYachtModelDto obiekt DTO z danymi nowego modelu jachty
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public void addYachtModel(NewYachtModelDto newYachtModelDto) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                YachtModel yachtModel = ObjectMapperUtils.map(newYachtModelDto, YachtModel.class);
                yachtModelManager.addYachtModel(yachtModel);
                rollback = yachtModelManager.isLastTransactionRollback();
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
     * Metoda, która zwraca listę wszystkich modeli jachtów.
     *
     * @return lista wszystkich modeli jachtów
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public List<ListYachtModelDto> getAllYachtModels() {
        return ObjectMapperUtils.mapAll(yachtModelManager.getAllYachtModels(), ListYachtModelDto.class);
    }

    /**
     * Metoda, która zwraca szczegóły danego modelu jachtu.
     *
     * @param yachtModelId id danego modelu jachtu
     * @return dto szczegółów danego modelu jachtu
     * @throws AppBaseException wyjątek aplikacyjny jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public YachtModelDetailsDto getYachtModelById(Long yachtModelId) throws AppBaseException {
        YachtModel yachtModel = yachtModelManager.getYachtModelById(yachtModelId);
        return ObjectMapperUtils.map(yachtModel, YachtModelDetailsDto.class);
    }

    /**
     * Metoda która zapisuje wprowadzone przez managera zmiany w modelu jachtu.
     *
     * @param editYachtModelDto obiekt DTO przeznaczony do edycji modelu jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public void editYachtModel(EditYachtModelDto editYachtModelDto) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                yachtModelEditEntity.setManufacturer(editYachtModelDto.getManufacturer());
                yachtModelEditEntity.setBasicPrice(editYachtModelDto.getBasicPrice());
                yachtModelEditEntity.setCapacity(editYachtModelDto.getCapacity());
                yachtModelEditEntity.setGeneralDescription(editYachtModelDto.getGeneralDescription());
                yachtModelManager.editYachtModel(this.yachtModelEditEntity);
                rollback = yachtModelManager.isLastTransactionRollback();
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
     * Metoda która deaktywuje model jachtu o podanym id.
     *
     * @param yachtModelId id modelu jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public void deactivateYachtModel(Long yachtModelId) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                yachtModelManager.deactivateYachtModel(yachtModelId);
                rollback = yachtModelManager.isLastTransactionRollback();
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
     * Metoda która zwraca model jachtu do edycji o podanym id
     *
     * @param yachtModelId id modelu jachtu
     * @return EditYachtModelDto
     * @throws AppBaseException wyjątek aplikacyjny, jeśli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"ADMINISTRATOR", "MANAGER", "CLIENT"})
    public EditYachtModelDto getEditYachtModelDtoById(Long yachtModelId) throws AppBaseException {
        this.yachtModelEditEntity = yachtModelManager.getYachtModelById(yachtModelId);
        return ObjectMapperUtils.map(this.yachtModelEditEntity, EditYachtModelDto.class);
    }
}
