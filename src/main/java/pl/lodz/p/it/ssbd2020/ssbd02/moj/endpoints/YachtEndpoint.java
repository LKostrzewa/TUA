package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;



import pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.NewYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.UpdateYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.YachtListDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.YachtManager;
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
public class YachtEndpoint {

    @Inject
    private YachtManager yachtManager;

    public void addYacht(NewYachtDto newYachtDto) {
        Yacht yacht = ObjectMapperUtils.map(newYachtDto, Yacht.class);
        yachtManager.addYacht(yacht);
    }

     public List<YachtListDto> getAllYachts() {
        return yachtManager.getAllYachts().stream().map(n -> ObjectMapperUtils.map(n, YachtListDto.class)).collect(Collectors.toList());
    }

    public YachtDto getYachtById(Long yachtId) {
        Yacht yacht = yachtManager.getYachtById(yachtId);
        return ObjectMapperUtils.map(yacht, YachtDto.class);
    }

    public void updateYacht(Long yachtId, UpdateYachtDto updateYachtDto) {
        Yacht yachtToUpdate = ObjectMapperUtils.map(updateYachtDto, Yacht.class);
        yachtManager.updateYacht(yachtId, yachtToUpdate);
    }

    public void deactivateYacht(Long yachtId) {
        yachtManager.deactivateYacht(yachtId);
    }

}
