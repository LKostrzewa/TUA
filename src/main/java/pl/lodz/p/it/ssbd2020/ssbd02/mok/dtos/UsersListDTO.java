package pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos;

import java.util.Objects;

public class UsersListDTO implements Comparable<UsersListDTO> {
    private Long id;
    private String login;
    private String email;
    private boolean locked;
    private boolean activated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        UsersListDTO usersListDTO = (UsersListDTO) obj;

        if (!Objects.equals(login, usersListDTO.login)) return false;
        return !Objects.equals(email, usersListDTO.email);
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UsersListDTO{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public int compareTo(UsersListDTO usersListDTO) {
        return this.login.compareTo(usersListDTO.login);
    }
}
