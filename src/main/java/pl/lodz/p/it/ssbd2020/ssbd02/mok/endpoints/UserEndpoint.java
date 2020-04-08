package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;



import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.AccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserAccessLevelFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.BCryptPasswordHash;


import javax.ejb.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Stateful
public class UserEndpoint {

public final static String CLIENT_ACCESS_LEVEL = "CLIENT";

    @EJB
    private UserAccessLevelFacade userAccessLevelFacade;

    @EJB
    private AccessLevelFacade accessLevelFacade;

    @EJB
    UserFacade userFacade;


    public void registerNewUser(UserDTO userDTO) {
        BCryptPasswordHash bCryptPasswordHash = new BCryptPasswordHash();

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
        user.setPassword(bCryptPasswordHash.generate(userDTO.getPassword().toCharArray()));
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userFacade.create(user);

        UserAccessLevel userAccessLevel = new UserAccessLevel();
        userAccessLevel.setVersion(1);
        userAccessLevel.setBusinessKey(UUID.randomUUID());
        userAccessLevel.setAccessLevelId(accessLevelFacade.findByAccessLevelName(CLIENT_ACCESS_LEVEL));
        userAccessLevel.setUserId(user);
        userAccessLevelFacade.create(userAccessLevel);

    }
    public List<User> getAll() {
        return userFacade.findAll();
    }

    public void edit(User user) {
        userFacade.edit(user);
    }
}
