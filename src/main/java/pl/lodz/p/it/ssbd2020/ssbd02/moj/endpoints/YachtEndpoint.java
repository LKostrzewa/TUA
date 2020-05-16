package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.EditYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.NewYachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.yacht.YachtListDto;

import java.util.List;

public interface YachtEndpoint {
    void addYacht(NewYachtDto newYachtDto) throws AppBaseException;

    List<YachtListDto> getAllYachts();

    YachtDto getYachtById(Long yachtId) throws AppBaseException;

    EditYachtDto getEditYachtDtoById(Long yachtId) throws AppBaseException;

    void editYacht(EditYachtDto editYachtDto) throws AppBaseException;

    void deactivateYacht(Long yachtId) throws AppBaseException;


}
