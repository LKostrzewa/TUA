package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;


import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.UserDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class UserEndpoint implements Serializable {
    @Inject
    private UserManager userManager;

    public void registerNewUser(UserDTO userDTO) {
        User user = ObjectMapperUtils.map(userDTO, User.class);
        userManager.registerNewUser(user);
    }

    public List<User> getAll() {
        return userManager.getAll();
    }

    public User find(Long id) {
        return userManager.find(id);
    }

    public void edit(User user) {
        userManager.edit(user);
    }
}
