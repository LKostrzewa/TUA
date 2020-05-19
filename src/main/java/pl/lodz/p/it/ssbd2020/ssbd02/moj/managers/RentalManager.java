package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.RentalStatus;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.RentalFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.RentalStatusFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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

    public void addRental(Rental rental) throws AppBaseException {
        rentalFacade.create(rental);
    }


    /**
     * Metoda, która zwraca listę wszystkich wypożyczeń
     *
     * @return lista wypożyczeń użytkownika o podanym loginie
     */
    @RolesAllowed("getAllRentals")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Rental> getAllRentals() {
        return rentalFacade.findAll();
    }

    @RolesAllowed("getRentalById")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Rental getRentalById(Long rentalId) throws AppBaseException {
        //TODO poprawic na odpowiedni wyjątek
        return rentalFacade.find(rentalId).orElseThrow(() -> new AppBaseException("nie ma tego wypozyczenia"));
    }

    /**
     * Metoda, która zwraca listę wypożyczeń danego klienta.
     *
     * @param userLogin login użytkownika
     * @return lista wypożyczeń użytkownika o podanym loginie
     */
    @RolesAllowed("getRentals")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Rental> getAllRentalsByUser(String userLogin) {
        return rentalFacade.findAll()
                .stream()
                .filter(rental -> rental.getUser().getLogin().equals(userLogin))
                .collect(Collectors.toList());
    }

    /**
     * Metoda, która zwraca wszystkie wypożyczenia na dany jacht.
     *
     * @param yachtName nazwa yachtu
     * @return lista wypożyczeń użytkownika o podanym loginie
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getRentalsByYacht")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Rental> getAllRentalsByYacht(String yachtName) throws AppBaseException{
        return rentalFacade.findAllByYacht(yachtName);
                //.stream()
                //.filter(rental -> rental.getYacht().getName().equals(yachtName))
                //.collect(Collectors.toList());
    }

    public void editRental(Rental rental) throws AppBaseException {
        //TODO poprawic na odpowiedni wyjątek
        Rental rentalToEdit = rentalFacade.find(rental.getId()).orElseThrow(() -> new AppBaseException("nie ma tego wypozyczenia"));
        //USTAWIANIE PÓL
        rentalFacade.edit(rental);
    }

    public void cancelRental(Long rentalId) throws AppBaseException {
        Rental rentalToCancel = getRentalById(rentalId);
        RentalStatus rentalStatus = rentalStatusFacade.findByName("CANCELED");
        rentalToCancel.setRentalStatus(rentalStatus);
        rentalFacade.edit(rentalToCancel);
    }

    public void updateRentalStatus() {
        List<Rental> allRentals = rentalFacade.findAll();
        for (Rental rental : allRentals) {
            //tu logika tego wszyskiego
            //rentalFacade.edit(rental);
        }
    }
}
