package pl.lodz.p.it.ssbd2020.ssbd02.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd02.entities.User;

import java.util.List;

public interface UserFacadeLocal {

    List<User> findAll();

}
