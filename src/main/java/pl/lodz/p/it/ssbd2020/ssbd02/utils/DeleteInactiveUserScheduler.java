package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import pl.lodz.p.it.ssbd2020.ssbd02.mok.managers.UserManager;

import javax.annotation.security.RunAs;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.inject.Inject;


@RunAs("SYSTEM")
public class DeleteInactiveUserScheduler {

    @Inject
    private UserManager userManager;

    //Uruchomienie schedulera o 3.00
    @Schedules({
            @Schedule(hour = "3")
    })

    public void performTask(){
        userManager.deleteInactiveUsers();
    }
}
