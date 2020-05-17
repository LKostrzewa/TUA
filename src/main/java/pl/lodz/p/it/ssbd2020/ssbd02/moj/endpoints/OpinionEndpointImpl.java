package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.Opinion;
import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.EditOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.NewOpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.opinion.OpinionDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.managers.OpinionManager;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.LoggerInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd02.utils.ObjectMapperUtils;

import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.io.Serializable;
import java.util.List;

@Stateful
@Interceptors(LoggerInterceptor.class)
public class OpinionEndpointImpl implements Serializable, OpinionEndpoint {
    @Inject
    private OpinionManager opinionManager;

    /**
     * Metoda, która dodaje nową opinię
     *
     * @param newOpinionDto obiekt DTO z danymi nowej opinii.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("addOpinion")
    public void addOpinion(NewOpinionDto newOpinionDto) throws AppBaseException {
        Opinion opinion = ObjectMapperUtils.map(newOpinionDto, Opinion.class);
        opinionManager.addOpinion(opinion);
    }

    /**
     * Metoda pobierająca wszystkie opinie przypisane do danego jachtu
     *
     * @param yachtId identyfikator jachtu
     * @return lista opini dla danego jachtu
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getAllOpinionsByYacht")
    public List<OpinionDto> getAllOpinionsByYacht(Long yachtId) throws AppBaseException{
        return ObjectMapperUtils.mapAll(opinionManager.getAllOpinionsByYacht(yachtId), OpinionDto.class);
    }

    /**
     * Metoda zwracająca opinię do edycji na podstawie przekazanego identyfikatora
     *
     * @param opinionId identyfikator opinii
     * @return opinia do edycji
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("getOpinionById")
    public EditOpinionDto getOpinionById(Long opinionId) throws AppBaseException{
        Opinion opinion = opinionManager.getOpinionById(opinionId);
        return ObjectMapperUtils.map(opinion, EditOpinionDto.class);
    }

    /**
     * Metoda służąca do zapisu nowej wersji opinii
     *
     * @param opinionId identyfikator opinii
     * @param editOpinionDto obiekt DTO z danymi edytowanej opinii.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    @RolesAllowed("editOpinion")
    public void editOpinion(Long opinionId, EditOpinionDto editOpinionDto) throws AppBaseException{
        Opinion opinionToEdit = ObjectMapperUtils.map(editOpinionDto, Opinion.class);
        opinionManager.editOpinion(opinionId, opinionToEdit);
    }
}
