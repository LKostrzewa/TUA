package pl.lodz.p.it.ssbd2020.ssbd02.utils;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.RentalManager;

import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.inject.Inject;

public class UpdateRentalStatusScheduler {

    @Inject
    private RentalManager rentalManager;

    @Schedules({
            @Schedule(hour = "10")
    })

    public void performTask() {
        rentalManager.updateRentalStatus();
    }
}
