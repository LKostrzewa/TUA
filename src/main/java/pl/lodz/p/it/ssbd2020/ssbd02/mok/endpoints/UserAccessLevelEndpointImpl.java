package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppEJBTransactionRolledbackException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.RepeatedRollBackException;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDetailsDto;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.AccessLevelManager;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserAccessLevelManager;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementacja interfejsu UserAccessLevelEndpoint
 */
@Stateful
@Interceptors(LoggerInterceptor.class)
public class UserAccessLevelEndpointImpl implements Serializable, UserAccessLevelEndpoint {
    private String ADMIN_ACCESS_LEVEL;
    private String MANAGER_ACCESS_LEVEL;
    private String CLIENT_ACCESS_LEVEL;
    @Inject
    private UserAccessLevelManager userAccessLevelManager;
    @Inject
    private UserManager userManager;

    private User user;

    PropertyReader propertyReader = new PropertyReader();
    Integer METHOD_INVOCATION_LIMIT;
    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    @PostConstruct
    private void init() {
        PropertyReader propertyReader = new PropertyReader();
        ADMIN_ACCESS_LEVEL = propertyReader.getProperty("config", "ADMIN_ACCESS_LEVEL");
        MANAGER_ACCESS_LEVEL = propertyReader.getProperty("config", "MANAGER_ACCESS_LEVEL");
        CLIENT_ACCESS_LEVEL = propertyReader.getProperty("config", "CLIENT_ACCESS_LEVEL");
    }
    /**
     * Metoda, która zwraca obiekt UserAccessLevelDto zawierający informacje o poziomach dostępu danego użytkownika
     *
     * @param userId identyfikator użytkownika.
     * @return obiekt klasy UserAccessLevelDto
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem.
     */
    @RolesAllowed("findUserAccessLevelById")
    public UserAccessLevelDto findUserAccessLevelById(Long userId) throws AppBaseException{
        user = userManager.getUserById(userId);
        UserAccessLevelDto userAccessLevelDto = new UserAccessLevelDto();
        userAccessLevelDto.setUserDetailsDto(ObjectMapperUtils.map(user, UserDetailsDto.class));
        for (UserAccessLevel level : user.getUserAccessLevels()) {
            if (level.getAccessLevel().getName().equals(ADMIN_ACCESS_LEVEL)) {
                userAccessLevelDto.getAdmin().setLeft(true);
                userAccessLevelDto.getAdmin().setRight(true);
            }
            if (level.getAccessLevel().getName().equals(MANAGER_ACCESS_LEVEL)) {
                userAccessLevelDto.getManager().setLeft(true);
                userAccessLevelDto.getManager().setRight(true);
            }
            if (level.getAccessLevel().getName().equals(CLIENT_ACCESS_LEVEL)) {
                userAccessLevelDto.getClient().setLeft(true);
                userAccessLevelDto.getClient().setRight(true);
            }
        }
        return userAccessLevelDto;
    }

    /**
     * Metoda, która zwraca obiekt UserAccessLevelDto zawierający informacje o poziomach dostępu danego użytkownika
     *
     * @return obiekt klasy UserAccessLevelDto
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem.
     */
    @RolesAllowed("findUserAccessLevelByLogin")
    public UserAccessLevelDto findUserAccessLevelByLogin() throws AppBaseException {
        user = userManager.getUserByLogin(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());
        Collection<UserAccessLevel> userAccessLevels = user.getUserAccessLevels();
        UserAccessLevelDto userAccessLevelDto = new UserAccessLevelDto();
        userAccessLevelDto.setUserDetailsDto(ObjectMapperUtils.map(user, UserDetailsDto.class));
        for (UserAccessLevel level : userAccessLevels) {
            if (level.getAccessLevel().getName().equals(ADMIN_ACCESS_LEVEL))
                userAccessLevelDto.getAdmin().setLeft(true);
            if (level.getAccessLevel().getName().equals(MANAGER_ACCESS_LEVEL))
                userAccessLevelDto.getManager().setLeft(true);
            if (level.getAccessLevel().getName().equals(CLIENT_ACCESS_LEVEL))
                userAccessLevelDto.getClient().setLeft(true);
        }
        return userAccessLevelDto;
    }

    /**
     * Metoda, która modyfikuje przypisane do użytkownika poziomy dostępu.
     *
     * @param userAccessLevelDto obiekt zawierająy informacje o obecnych i pożądanych poziomach dotępu.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem.
     */
    @RolesAllowed("editUserAccessLevels")
    public void editUserAccessLevels(UserAccessLevelDto userAccessLevelDto) throws AppBaseException {
        int methodInvocationCounter = 0;
        boolean rollback;
        do {
            try {
                List<MutablePair<Boolean, Boolean>> userAccessLevelList = new ArrayList<>();
                userAccessLevelList.add(userAccessLevelDto.getAdmin());
                userAccessLevelList.add(userAccessLevelDto.getManager());
                userAccessLevelList.add(userAccessLevelDto.getClient());
                userAccessLevelManager.editUserAccessLevel(this.user, userAccessLevelList);

                rollback = userAccessLevelManager.isLastTransactionRollback();
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
