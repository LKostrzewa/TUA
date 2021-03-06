package pl.lodz.p.it.ssbd2020.ssbd02.moj.schedulers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.RentalStatus;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.RentalFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.RentalStatusFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.PropertyReader;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.ejb.*;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa reprezentująca Scheduler.
 */
@Singleton(name = "UpdateRentalStatusScheduler")
@Named("UpdateRentalStatusScheduler")
@Interceptors(LoggerInterceptor.class)
public class UpdateRentalStatusScheduler {

    @Inject
    private RentalFacade rentalFacade;
    @Inject
    private RentalStatusFacade rentalStatusFacade;

    private static final Logger LOGGER = Logger.getGlobal();

    private final PropertyReader propertyReader = new PropertyReader();

    /**
     * Metoda aktualizująca stany rezerwacji. Metoda jest wywoływana codziennie o godzinie 10.00.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @PermitAll
    @Schedule(hour = "10")
    public void performTask() {
        try {
            String PENDING_RENTAL_STATUS = propertyReader.getPropertyWithoutLocale("config", "PENDING_STATUS");
            String STARTED_RENTAL_STATUS = propertyReader.getPropertyWithoutLocale("config", "STARTED_STATUS");
            String FINISHED_RENTAL_STATUS = propertyReader.getPropertyWithoutLocale("config", "FINISHED_STATUS");
            List<Rental> allRentals = rentalFacade.findAll();
            List<RentalStatus> rentalStatuses = rentalStatusFacade.findAll();
            for (Rental rental : allRentals) {
                if (rental.getRentalStatus().getName().equals(STARTED_RENTAL_STATUS)) {

                    if (rental.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isBefore(LocalDateTime.now())) {

                        rental.setRentalStatus(getRentalStatusByName(rentalStatuses, FINISHED_RENTAL_STATUS));
                        rentalFacade.edit(rental);
                    }
                }
                if (rental.getRentalStatus().getName().equals(PENDING_RENTAL_STATUS)) {

                    if (rental.getBeginDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().isBefore(LocalDateTime.now())) {

                        rental.setRentalStatus(getRentalStatusByName(rentalStatuses, STARTED_RENTAL_STATUS));
                        rentalFacade.edit(rental);
                    }
                }
            }
        } catch (AppBaseException e) {
            LOGGER.log(Level.SEVERE,e.getLocalizedMessage());
        }
    }

    private RentalStatus getRentalStatusByName(List<RentalStatus> rentalStatuses, String name) throws AppBaseException {
        for (RentalStatus rentalStatus : rentalStatuses) {
            if(rentalStatus.getName().equals(name)){
                return rentalStatus;
            }
        }
        throw AppNotFoundException.createRentalStatusNotFoundException();
    }
}
