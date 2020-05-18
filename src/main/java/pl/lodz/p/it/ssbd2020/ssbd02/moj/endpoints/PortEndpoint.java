package pl.lodz.p.it.ssbd2020.ssbd02.moj.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.EditPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.NewPortDto;
import pl.lodz.p.it.ssbd2020.ssbd02.moj.dtos.port.PortDetailsDto;

import java.util.List;

public interface PortEndpoint {

    /**
     * Metoda, służy do dodawania nowych portów do bazy danych.
     *
     * @param newPortDto obiekt DTO z danymi nowego portu.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    public void addPort(NewPortDto newPortDto) throws AppBaseException;

    /**
     * Metoda, która zapisuje wprowadzone zmiany w porcie.
     *
     * @param editPortDto obiekt DTO z danymi portu do edycji.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    public void editPort(EditPortDto editPortDto) throws AppBaseException;

    /**
     * Metoda, która deaktywuje port.
     *
     * @param portId id jachtu.
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    public void deactivatePort(long portId) throws AppBaseException;

    /**
     * Metoda, która zwraca wszystkie porty.
     *
     * @return lista portów
     */
    public List<PortDetailsDto> getAllPorts();


    public PortDetailsDto getPortById(Long portId) throws AppBaseException;

    /**
     * Metoda, która zwraca port o podanym id.
     *
     * @param portId id portu.
     * @return PortDetailsDto
     * @throws AppBaseException wyjątek aplikacyjny, jesli operacja zakończy się niepowodzeniem
     */
    public PortDetailsDto getPortDetailsById(Long portId) throws AppBaseException;
}
