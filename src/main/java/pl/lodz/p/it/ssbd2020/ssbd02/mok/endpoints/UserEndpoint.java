package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;


import pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserAccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;

import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;
import java.util.UUID;

@Stateful
//@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UserEndpoint {

public final static String CLIENT_ACCESS_LEVEL = "CLIENT";

    @EJB
    private UserAccessLevelFacade userAccessLevelFacade;

    @EJB
    private AccessLevelFacade accessLevelFacade;

    @EJB
    UserFacade userFacade;
//    @Resource
//    private SessionContext sessionContext;

    public void registerNewUser(UserDTO userDTO) {
        User user = new User();
        user.setVersion(1);
        user.setVersion_user_details(1);
        user.setBusinessKey_user_details(UUID.randomUUID());
        user.setBusinessKey(UUID.randomUUID());
        user.setLogin(userDTO.getLogin());
        user.setActivated(false);
        user.setLocked(false);
        user.setCreated(new Date());
        user.setInvalidLoginAttemps(0);
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userFacade.create(user);

        UserAccessLevel userAccessLevel = new UserAccessLevel();
        userAccessLevel.setVersion(1);
        userAccessLevel.setBusinessKey(UUID.randomUUID());
        userAccessLevel.setAccessLevelId(accessLevelFacade.findByLogin(CLIENT_ACCESS_LEVEL));
        userAccessLevel.setUserId(user);
        userAccessLevelFacade.create(userAccessLevel);
    }
}
