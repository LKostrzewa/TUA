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
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UserEndpoint extends AbstractEndpoint implements SessionSynchronization  {


    @EJB
    private UserAccessLevelFacade userAccessLevelFacade;

    @EJB
    private AccessLevelFacade accessLevelFacade;

    @EJB
    UserFacade userFacade;
    @Resource
    private SessionContext sessionContext;

    public void registerNewUser(UserDTO userDTO) {
        User user = new User();
        user.setVersion(1);
        user.setBusinessKey(UUID.randomUUID());
        user.setLogin(userDTO.getLogin());
        //user.setActivated(false);
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        userDTO.setFirstName(userDTO.getFirstName());
        userDTO.setLastName(userDTO.getLastName());
        userDTO.setPhoneNumber(userDTO.getPhoneNumber());

        //user.setActivated(true);
        //user.setLocked(true);
//        user.setCreated(new Date());
        //user.setInvalidLoginAttemps(0);
        userFacade.create(user);

        AccessLevel accessLevel = new AccessLevel();
        accessLevel.setName("KLIENT");
        accessLevelFacade.create(accessLevel);

        UserAccessLevel userAccessLevel = new UserAccessLevel();
        userAccessLevel.setAccessLevelId(accessLevel);
        userAccessLevel.setUserId(user);
        userAccessLevelFacade.create(userAccessLevel);
    }
}
