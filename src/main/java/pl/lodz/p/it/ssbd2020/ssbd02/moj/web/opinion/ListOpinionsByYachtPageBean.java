package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.opinion;

import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.OpinionDto;
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
    private List<OpinionDto> yachtOpinions;

    public List<OpinionDto> getYachtOpinions() {
        return yachtOpinions;
    }

    public void setYachtOpinions(List<OpinionDto> yachtOpinions) {
        this.yachtOpinions = yachtOpinions;
    }

    @PostConstruct
    private void init(Long id) {
        this.yachtOpinions = opinionEndpoint.getAllOpinionsByYacht(id);
    }
}
