package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserAccessLevelFacade;


import javax.ejb.*;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Singleton
@Startup
@Interceptors(LoggerInterceptor.class)
public class DeleteInactiveUserScheduler {

    @Inject
    private UserFacade userFacade;
    @Inject
    private UserAccessLevelFacade userAccessLevelFacade;
    //Uruchomienie schedulera o 3.00

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Schedule(hour = "*", minute = "*")
    public void performTask(){
        List<User> users = userFacade.findAll();
        for (User user : users) {
            if((!user.isActivated())&&(user.getCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isBefore(LocalDateTime.now().minusDays(1)))){
                /*for (UserAccessLevel userAccessLevel: user.getUserAccessLevels()) {
                    userAccessLevelFacade.remove(userAccessLevel);
                }*/

                userFacade.remove(user);
            }
        }
    }
}
