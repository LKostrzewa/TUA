package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.RentalManager;

import javax.annotation.security.RunAs;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.inject.Inject;

@RunAs("SYSTEM")
public class UpdateRentalStatusScheduler {

    @Inject
    private RentalManager rentalManager;

    @Schedules({
            @Schedule(hour = "10")
    })


    public void performTask() throws AppBaseException {
        rentalManager.updateRentalStatus();
    }
}
