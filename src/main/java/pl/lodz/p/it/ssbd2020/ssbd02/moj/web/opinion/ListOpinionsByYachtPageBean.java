package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.opinion;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.OpinionDTO;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.OpinionEndpoint;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ListOpinionsByYachtPageBean {
    @Inject
    private OpinionEndpoint opinionEndpoint;

    private List<OpinionDTO> yachtOpinions;

    @PostConstruct
    private void init(Long id){
        this.yachtOpinions = opinionEndpoint.getAllOpinionsByYacht(id);
    }
    public List<OpinionDTO> getYachtOpinions() {
        return yachtOpinions;
    }

    public void setYachtOpinions(List<OpinionDTO> yachtOpinions) {
        this.yachtOpinions = yachtOpinions;
    }
}
