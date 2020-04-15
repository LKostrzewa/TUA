package pl.lodz.p.it.ssbd2020.ssbd02.moj.managers;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Opinion;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental;
import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facade.OpinionFacade;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.facade.RentalFacade;
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
public class OpinionManager {

    @Inject
    private RentalFacade rentalFacade;
    @Inject
    private OpinionFacade opinionFacade;

    public void addOpinion(Opinion opinion){
        opinionFacade.create(opinion);
    }

    public List<Opinion> getAllOpinionsByYacht(Long yachtId){
        return rentalFacade.findAll().stream()
                .filter(rental -> rental.getYacht().getId().equals(yachtId))
                .map(Rental::getOpinion)
                .collect(Collectors.toList());
    }

    public Opinion getOpinionById(Long opinionId){
        return opinionFacade.find(opinionId);
    }

    public void updateOpinion(Long opinionId, Opinion opinionToUpdate){
        opinionToUpdate.setId(opinionId);
        opinionToUpdate.setEdited(true);
        opinionFacade.edit(opinionToUpdate);
    }
}
