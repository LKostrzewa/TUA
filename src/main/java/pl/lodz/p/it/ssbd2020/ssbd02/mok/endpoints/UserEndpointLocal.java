package pl.lodz.p.it.ssbd2020.ssbd02.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;

import java.util.List;

public interface UserEndpointLocal {

    List<User> getAll();
}
