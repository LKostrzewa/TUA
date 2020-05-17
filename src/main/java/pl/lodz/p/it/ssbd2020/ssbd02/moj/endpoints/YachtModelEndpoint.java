package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yachtModel.NewYachtModelDto;

public interface YachtModelEndpoint {
    void addYachtModel(NewYachtModelDto newYachtModelDTO) throws AppBaseException;
}
