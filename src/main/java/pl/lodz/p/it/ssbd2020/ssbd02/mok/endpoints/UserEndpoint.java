package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;



import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;


import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserEndpoint {

    @Inject
    private UserManager userManager;

    public void registerNewUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        userManager.registerNewUser(user);

    }

    public List<User> getAll() {
        return userManager.getAll();
    }

    public void edit(User user) {
        userManager.edit(user);
    }
}
