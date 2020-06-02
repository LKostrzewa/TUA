package pl.lodz.p.it.ssbd2020.ssbd02.mok.schedulers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * Klasa reprezentująca Scheduler.
 */
@Singleton
@Startup
@RunAs("deleteInactiveUsers")
@Interceptors(LoggerInterceptor.class)
public class DeleteInactiveUserScheduler {

    @Inject
    private UserFacade userFacade;

    //Uruchomienie schedulera o 3.00 @Schedule(hour = "3")
    //Uruchomienie schedulera co 1 minutę @Schedule(hour = "*", minute = "*")

    /**
     * Metoda do usuwania z bazy danych kont użytkowników. Konto jest usuwane jeżeli nie jest aktywne przez
     * ponad dobę licząc od daty utworzenia. Metoda jest wywoływana codziennie o godzinie 3.00.
     */
    @RolesAllowed("deleteInactiveUsers")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Schedule(hour = "3")
    public void performTask() {
        List<User> users = userFacade.findAll();
        for (User user : users) {
            if ((!user.isActivated()) && (user.getCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isBefore(LocalDateTime.now().minusDays(1)))) {
                userFacade.remove(user);
            }
        }
    }
}
