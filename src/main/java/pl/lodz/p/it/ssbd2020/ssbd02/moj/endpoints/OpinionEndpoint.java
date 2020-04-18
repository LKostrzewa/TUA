package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Opinion;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.EditOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.NewOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.OpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.OpinionManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@Stateful
@LocalBean
@Interceptors(LoggerInterceptor.class)
public class OpinionEndpoint implements Serializable {
    @Inject
    private OpinionManager opinionManager;

    public void addOpinion(NewOpinionDto newOpinionDto) {
        Opinion opinion = ObjectMapperUtils.map(newOpinionDto, Opinion.class);
        opinionManager.addOpinion(opinion);
    }

    public List<OpinionDto> getAllOpinionsByYacht(Long yachtId) {
        return ObjectMapperUtils.mapAll(opinionManager.getAllOpinionsByYacht(yachtId), OpinionDto.class);
    }

    public EditOpinionDto getOpinionById(Long opinionId) {
        Opinion opinion = opinionManager.getOpinionById(opinionId);
        return ObjectMapperUtils.map(opinion, EditOpinionDto.class);
    }

    public void editOpinion(Long opinionId, EditOpinionDto editOpinionDto) {
        Opinion opinionToEdit = ObjectMapperUtils.map(editOpinionDto, Opinion.class);
        opinionManager.editOpinion(opinionId, opinionToEdit);
    }
}
