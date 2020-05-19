package pl.lodz.p.it.ssbd2020.ssbd02.moj.web.yachtModel;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.ListYachtModelDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints.YachtModelEndpoint;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ListYachtModelPageBean implements Serializable {
    @Inject
    private YachtModelEndpoint yachtModelEndpoint;
    private List<ListYachtModelDto> yachtModels;

    public List<ListYachtModelDto> getYachtModels() {
        return yachtModels;
    }

    public void setYachtModels(List<ListYachtModelDto> yachtModels) {
        this.yachtModels = yachtModels;
    }

    @PostConstruct
    private void init() {
        this.yachtModels = yachtModelEndpoint.getAllYachtModels();
    }

    public String deactivateYachtModel(Long yachtModelID) throws AppBaseException {
        yachtModelEndpoint.deactivateYachtModel(yachtModelID);
        return "/manager/listYachtModels.xhtml?faces-redirect=true";
    }
}
