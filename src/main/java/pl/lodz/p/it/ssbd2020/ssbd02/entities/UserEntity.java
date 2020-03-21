package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "user", schema = "public", catalog = "ssbd02")
public class UserEntity {
    private long id;
    private long version;
    private Object businessKey;
    private String login;
    private String password;
    private String email;
    private boolean locked;
    private boolean active;
    private Timestamp created;
    private Timestamp lastValidLogin;
    private Timestamp lastInvalidLogin;
    private int invalidLoginAttemps;
    private Collection<UserAccessLevelEntity> userAccessLevelsById;
    private Collection<UserDetailsEntity> userDetailsById;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "version", nullable = false)
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Basic
    @Column(name = "business_key", nullable = false)
    public Object getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(Object businessKey) {
        this.businessKey = businessKey;
    }

    @Basic
    @Column(name = "login", nullable = false, length = 32)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 64)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "locked", nullable = false)
    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Basic
    @Column(name = "active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Basic
    @Column(name = "created", nullable = false)
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Basic
    @Column(name = "last_valid_login", nullable = true)
    public Timestamp getLastValidLogin() {
        return lastValidLogin;
    }

    public void setLastValidLogin(Timestamp lastValidLogin) {
        this.lastValidLogin = lastValidLogin;
    }

    @Basic
    @Column(name = "last_invalid_login", nullable = true)
    public Timestamp getLastInvalidLogin() {
        return lastInvalidLogin;
    }

    public void setLastInvalidLogin(Timestamp lastInvalidLogin) {
        this.lastInvalidLogin = lastInvalidLogin;
    }

    @Basic
    @Column(name = "invalid_login_attemps", nullable = false)
    public int getInvalidLoginAttemps() {
        return invalidLoginAttemps;
    }

    public void setInvalidLoginAttemps(int invalidLoginAttemps) {
        this.invalidLoginAttemps = invalidLoginAttemps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (version != that.version) return false;
        if (locked != that.locked) return false;
        if (active != that.active) return false;
        if (invalidLoginAttemps != that.invalidLoginAttemps) return false;
        if (businessKey != null ? !businessKey.equals(that.businessKey) : that.businessKey != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (lastValidLogin != null ? !lastValidLogin.equals(that.lastValidLogin) : that.lastValidLogin != null)
            return false;
        if (lastInvalidLogin != null ? !lastInvalidLogin.equals(that.lastInvalidLogin) : that.lastInvalidLogin != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (version ^ (version >>> 32));
        result = 31 * result + (businessKey != null ? businessKey.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (locked ? 1 : 0);
        result = 31 * result + (active ? 1 : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (lastValidLogin != null ? lastValidLogin.hashCode() : 0);
        result = 31 * result + (lastInvalidLogin != null ? lastInvalidLogin.hashCode() : 0);
        result = 31 * result + invalidLoginAttemps;
        return result;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<UserAccessLevelEntity> getUserAccessLevelsById() {
        return userAccessLevelsById;
    }

    public void setUserAccessLevelsById(Collection<UserAccessLevelEntity> userAccessLevelsById) {
        this.userAccessLevelsById = userAccessLevelsById;
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<UserDetailsEntity> getUserDetailsById() {
        return userDetailsById;
    }

    public void setUserDetailsById(Collection<UserDetailsEntity> userDetailsById) {
        this.userDetailsById = userDetailsById;
    }
}
