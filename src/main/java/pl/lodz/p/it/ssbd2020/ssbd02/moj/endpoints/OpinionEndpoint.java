package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Opinion;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.NewOpinionDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.OpinionDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.UpdateOpinionDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.OpinionManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.stream.Collectors;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class OpinionEndpoint {

    @Inject
    private OpinionManager opinionManager;

    public void addOpinion(NewOpinionDTO newOpinionDTO){
        Opinion opinion = ObjectMapperUtils.map(newOpinionDTO, Opinion.class);
        opinionManager.addOpinion(opinion);
    }

    public List<OpinionDTO> getAllOpinionsByYacht(Long yachtId) {
        return opinionManager.getAllOpinionsByYacht(yachtId).stream().map(n -> ObjectMapperUtils.map(n, OpinionDTO.class)).collect(Collectors.toList());
    }

    public OpinionDTO getOpinionByID(Long opinionId) {
        Opinion opinion = opinionManager.getOpinionById(opinionId);
        return ObjectMapperUtils.map(opinion, OpinionDTO.class);
    }

    public void updateOpinion(Long opinionId, UpdateOpinionDTO updateOpinionDTO) {
        Opinion opinionToUpdate = ObjectMapperUtils.map(updateOpinionDTO, Opinion.class);
        opinionManager.updateOpinion(opinionId, opinionToUpdate);
    }
}
