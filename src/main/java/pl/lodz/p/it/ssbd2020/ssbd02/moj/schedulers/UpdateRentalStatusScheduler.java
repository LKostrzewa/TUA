package pl.lodz.p.it.ssbd2020.ssbd02.moj.schedulers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.RentalStatus;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.RentalFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.RentalStatusFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.annotation.security.RunAs;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.Date;
import java.util.List;

@Singleton
@Startup
@RunAs("updateRentalStatus")
@Interceptors(LoggerInterceptor.class)
public class UpdateRentalStatusScheduler {

    @Inject
    private RentalFacade rentalFacade;
    @Inject
    private RentalStatusFacade rentalStatusFacade;

    /**
     * Metoda aktualizująca stany rezerwacji. Metoda jest wywoływana codziennie o godzinie 10.00.
     */
    //@Schedule(hour = "10")
    @RolesAllowed("updateRentalStatus")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Schedule(hour = "10")
    public void performTask() {
        try {
            List<Rental> allRentals = rentalFacade.findAll();
            List<RentalStatus> rentalStatuses = rentalStatusFacade.findAll();
            for (Rental rental : allRentals) {
                if (rental.getRentalStatus().equals(rentalStatuses.stream().filter(rentalStatus -> rentalStatus.getName()
                        .equals("STARTED")).findAny().orElseThrow(AppNotFoundException::createRentalStatusNotFoundException)) && rental.getEndDate().before(new Date())) {

                    rental.setRentalStatus(rentalStatuses.stream().filter(rentalStatus -> rentalStatus.getName()
                            .equals("FINISHED")).findAny().orElseThrow(AppNotFoundException::createRentalStatusNotFoundException));

                    rentalFacade.edit(rental);
                }
                if (rental.getRentalStatus().equals(rentalStatuses.stream().filter(rentalStatus -> rentalStatus.getName()
                        .equals("PENDING")).findAny().orElseThrow(AppNotFoundException::createRentalStatusNotFoundException)) && rental.getBeginDate().after(new Date())) {

                    rental.setRentalStatus(rentalStatuses.stream().filter(rentalStatus -> rentalStatus.getName()
                            .equals("STARTED")).findAny().orElseThrow(AppNotFoundException::createRentalStatusNotFoundException));

                    rentalFacade.edit(rental);
                }
            }
        } catch (AppBaseException e) {
        }
    }
}
