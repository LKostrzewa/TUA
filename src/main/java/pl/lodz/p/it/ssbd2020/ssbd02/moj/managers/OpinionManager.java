package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Opinion;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppNotFoundException;
import pl.lodz.p.it.ssbd2020.ssbd02.managers.AbstractManager;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.OpinionFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facades.YachtFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.math.BigDecimal;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class OpinionManager extends AbstractManager implements SessionSynchronization {
    /*@Inject
    private RentalFacade rentalFacade;*/
    @Inject
    private OpinionFacade opinionFacade;
    @Inject
    private YachtFacade yachtFacade;

    /**
     * Metoda, która dodaje nową opinię
     *
     * @param opinion encja z nową opinią do dodania
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addOpinion")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void addOpinion(Opinion opinion) throws AppBaseException {
        opinionFacade.create(opinion);
        calculateAvgRating(opinion.getRental().getYacht().getId());
    }

    /**
     * Metoda pobierająca wszystkie opinie przypisane do danego jachtu
     *
     * @param yachtId identyfikator jachtu
     * @return lista opini dla danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getAllOpinionsByYacht")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List<Opinion> getAllOpinionsByYacht(Long yachtId) throws AppBaseException{
        return opinionFacade.getAllOpinionsByYacht(yachtId);
        /*return rentalFacade.findAll().stream()
                .filter(rental -> rental.getYacht().getId().equals(yachtId))
                .map(Rental::getOpinion)
                .collect(Collectors.toList());*/
    }

    /**
     * Metoda zwracająca opinię na podstawie przekazanego identyfikatora
     *
     * @param opinionId identyfikator opinii
     * @return encja opinii
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getOpinionById")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Opinion getOpinionById(Long opinionId) throws AppBaseException {
        return opinionFacade.find(opinionId).orElseThrow(AppNotFoundException::createOpinionNotFoundException);
    }

    /**
     * Metoda służąca do zapisu nowej wersji opinii
     *
     * @param opinionToEdit encja z danymi edytowanej opinii.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editOpinion")
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void editOpinion(Opinion opinionToEdit) throws AppBaseException {
        opinionToEdit.setEdited(true);
        opinionFacade.edit(opinionToEdit);
        calculateAvgRating(opinionToEdit.getRental().getYacht().getId());
    }

    /**
     * Metoda do wyliczenia nowej średniej ceny dla yachtu o podanym identyfikatorze
     * @param yachtId identyfikator jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    private void calculateAvgRating(Long yachtId) throws AppBaseException{
        Yacht yacht = yachtFacade.find(yachtId).orElseThrow(AppNotFoundException::createYachtNotFoundException);
        BigDecimal tmp = BigDecimal.valueOf(yacht.getRentals().stream()
                .map(Rental::getOpinion)
                .mapToDouble(Opinion::getRating)
                .average().orElse(Double.NaN));
        yacht.setAvgRating(tmp);
        yachtFacade.edit(yacht);
    }
}
