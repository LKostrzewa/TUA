package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import org.primefaces.model.FilterMeta;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.RentalStatus;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd02.managers.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.RentalFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.RentalStatusFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.UserFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class RentalManager extends AbstractManager implements SessionSynchronization {
    @Inject
    private RentalFacade rentalFacade;
    @Inject
    private RentalStatusFacade rentalStatusFacade;
    @Inject
    private UserFacade userFacade;
    @Inject
    private YachtFacade yachtFacade;

    /**
     * Metoda, która służy do dodania nowego wypożyczenia.
     *
     * @param rental obiekt encji nowego wypożyczenia
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addRental")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addRental(Rental rental) throws AppBaseException {
        User rentingUser = userFacade.findByLogin(rental.getUser().getLogin());

        Yacht yachtToRent = yachtFacade.findByName(rental.getYacht().getName());

        if (yachtToRent.getCurrentPort() == null)
            throw YachtPortChangedException.createYachtNotAssignedException(yachtToRent);

        if (!yachtToRent.getCurrentPort().isActive())
            throw EntityNotActiveException.createPortNotActiveException(yachtToRent.getCurrentPort());

        if (!yachtToRent.isActive())
            throw EntityNotActiveException.createYachtNotActiveException(yachtToRent);

        if (rentalFacade.interfere(rental))
            throw RentalPeriodInterferenceException.createRentalPeriodInterferenceException(rental);

        RentalStatus startedStatus = rentalStatusFacade.findByName("STARTED");
        BigDecimal bigDecimal = new BigDecimal(0);
        Rental newRental = new Rental(rental.getBeginDate(), rental.getEndDate(), bigDecimal, rentingUser, yachtToRent);

        newRental.setRentalStatus(startedStatus);

        rentalFacade.create(newRental);
    }

    /**
     * Metoda, która zwraca wypożyczenie o danym id.
     *
     * @param rentalId id wypożyczenia
     * @return Wypożyczenie o podanym Id
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed({"getRentalById", "getUserRentalDetails", "cancelRental"})
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Rental getRentalById(Long rentalId) throws AppBaseException {
        return rentalFacade.find(rentalId).orElseThrow(AppNotFoundException::createRentalNotFoundException);
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
     * Metoda, która anuluje wypożyczenie.
     *
     * @param rentalId Id wypożyczenia, które użytkownik chce anulować
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("cancelRental")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void cancelRental(Long rentalId) throws AppBaseException {
        Rental rentalToCancel = getRentalById(rentalId);
        if (rentalToCancel.getRentalStatus().getName().equals("PENDING")) {
            RentalStatus rentalStatus = rentalStatusFacade.findByName("CANCELED");
            rentalToCancel.setRentalStatus(rentalStatus);
            rentalFacade.edit(rentalToCancel);
        } else throw RentalNotCancelableException.createRentalNotCancelableException(rentalToCancel);
    }

    /**
     * Metoda, aktualizująca statusy rezerwacji.
     *
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("SYSTEM")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void updateRentalStatus() throws AppBaseException {
        List<Rental> allRentals = rentalFacade.findAll();
        List<RentalStatus> rentalStatuses = rentalStatusFacade.findAll();
        for (Rental rental : allRentals) {
            if(rental.getRentalStatus().equals(rentalStatuses.stream().filter(rentalStatus -> rentalStatus.getName().equals("STARTED")).findAny().orElseThrow(null))&&rental.getEndDate().before(new Date())){
                rental.setRentalStatus(rentalStatuses.stream().filter(rentalStatus -> rentalStatus.getName().equals("FINISHED")).findAny().orElseThrow(null));
                rentalFacade.edit(rental);
            }
            if(rental.getRentalStatus().equals(rentalStatuses.stream().filter(rentalStatus -> rentalStatus.getName().equals("PENDING")).findAny().orElseThrow(null))&&rental.getBeginDate().after(new Date())){
                rental.setRentalStatus(rentalStatuses.stream().filter(rentalStatus -> rentalStatus.getName().equals("STARTED")).findAny().orElseThrow(null));
                rentalFacade.edit(rental);
            }
        }
    }


    /**
     * Metoda, która pobiera z bazy liczbę filtrowanych obiektów.
     *
     * @param filters para filtrowanych pól i ich wartości
     * @return liczba obiektów poddanych filtrowaniu
     */
    @RolesAllowed("getFilteredRowCountRental")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int getFilteredRowCount(Map<String, FilterMeta> filters) {
        return rentalFacade.getFilteredRowCount(filters);
    }

    /**
     * Metoda, która pobiera z bazy listę filtrowanych obiektów.
     *
     * @param first    numer pierwszego obiektu
     * @param pageSize rozmiar strony
     * @param filters  para filtrowanych pól i ich wartości
     * @return lista filtrowanych obiektów
     */
    @RolesAllowed("getResultListRental")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Rental> getResultList(int first, int pageSize, Map<String, FilterMeta> filters) {
        return rentalFacade.getResultList(first,pageSize,filters);
    }
}
