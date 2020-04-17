package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facade.RentalFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facade.RentalStatusFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facade.YachtFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.mok.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.stream.Collectors;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class RentalManager {
    @Inject
    private RentalFacade rentalFacade;
    @Inject
    private RentalStatusFacade rentalStatusFacade;
    @Inject
    private UserFacade userFacade;
    @Inject
    private YachtFacade yachtFacade;

    public void addRental(Rental rental) {
        rentalFacade.create(rental);
    }

    public List<Rental> getAllRentals() {
        return rentalFacade.findAll();
    }

    public Rental getRentalById(Long rentalId) {
        return rentalFacade.find(rentalId);
    }

    public List<Rental> getAllRentalsByUser(String userLogin) {
        return rentalFacade.findAll()
                .stream()
                .filter(rental -> rental.getUser().getLogin().equals(userLogin))
                .collect(Collectors.toList());
    }

    public List<Rental> getAllRentalsByYacht(String yachtName) {
        return rentalFacade.findAll()
                .stream()
                .filter(rental -> rental.getYacht().getName().equals(yachtName))
                .collect(Collectors.toList());
    }

    public void editRental(Rental rental) {
        Rental rentalToEdit = rentalFacade.find(rental.getId());
        //USTAWIANIE PÓL
        rentalFacade.edit(rental);
    }

    public void cancelRental(Long rentalId) {
        Rental rentalToCancel = getRentalById(rentalId);
//        RentalStatus rentalStatus = rentalStatusFacade.findByName("CANCELED");
//        rentalToCancel.setRentalStatus(rentalStatus);
        rentalFacade.edit(rentalToCancel);
    }
}
