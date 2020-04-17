package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;


import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos.*;
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

    public void registerNewUser(UserDto userDTO) {
        User user = ObjectMapperUtils.map(userDTO, User.class);
        userManager.registerNewUser(user);
    }

    public void addNewUser(AddUserDto userDTO) {
        User user = ObjectMapperUtils.map(userDTO, User.class);
        userManager.addNewUser(user);
    }

    public List<ListUsersDto> getAll() {
        return ObjectMapperUtils.mapAll(userManager.getAll(), ListUsersDto.class);
    }

    public ChangePasswordDto getChangePasswordDtoById(Long id) {
        return ObjectMapperUtils.map(userManager.getUserById(id), ChangePasswordDto.class);
    }

    public EditUserDto getEditUserDtoById(Long userId) {
        return ObjectMapperUtils.map(userManager.getUserById(userId), EditUserDto.class);
    }

    public UserDetailsDto getUserDetailsDtoById(Long userId) {
        return ObjectMapperUtils.map(userManager.getUserById(userId), UserDetailsDto.class);
    }

    public void editUser(EditUserDto editUserDto) {
        User user = ObjectMapperUtils.map(editUserDto, User.class);
        userManager.editUser(user);
    }

    public void editUserPassword(ChangePasswordDto changePasswordDto) {
        User user = ObjectMapperUtils.map(changePasswordDto, User.class);
        userManager.editUserPassword(user);
    }
}
