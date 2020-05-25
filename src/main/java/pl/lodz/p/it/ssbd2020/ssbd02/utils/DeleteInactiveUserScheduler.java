package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Singleton
@Startup
@Interceptors(LoggerInterceptor.class)
//@RunAs("SYSTEM")
public class DeleteInactiveUserScheduler {

    @Inject
    private UserFacade userFacade;

    //Uruchomienie schedulera o 3.00 @Schedule(hour = "3")
    //Uruchomienie schedulera co 1 minutę @Schedule(hour = "*", minute = "*")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    //@RolesAllowed("SYSTEM")
    @Schedule(hour = "3")
    public void performTask(){
        List<User> users = userFacade.findAll();
        for (User user : users) {
            if((!user.isActivated())&&(user.getCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isBefore(LocalDateTime.now().minusDays(1)))){
                userFacade.remove(user);
            }
        }
    }
}
