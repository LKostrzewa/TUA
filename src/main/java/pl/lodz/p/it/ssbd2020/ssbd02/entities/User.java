/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.TypeConverter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "\"user\"")
@SecondaryTable(name = "user_details", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
        @NamedQuery(name = "User.findByVersion", query = "SELECT u FROM User u WHERE u.version = :version"),
        @NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :login"),
        @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
        @NamedQuery(name = "User.findByLocked", query = "SELECT u FROM User u WHERE u.locked = :locked"),
        @NamedQuery(name = "User.findByActive", query = "SELECT u FROM User u WHERE u.activated = :active"),
        @NamedQuery(name = "User.findByCreated", query = "SELECT u FROM User u WHERE u.created = :created"),
        @NamedQuery(name = "User.findByLastValidLogin", query = "SELECT u FROM User u WHERE u.lastValidLogin = :lastValidLogin"),
        @NamedQuery(name = "User.findByLastInvalidLogin", query = "SELECT u FROM User u WHERE u.lastInvalidLogin = :lastInvalidLogin"),
        @NamedQuery(name = "User.findByInvalidLoginAttempts", query = "SELECT u FROM User u WHERE u.invalidLoginAttempts = :invalidLoginAttemps")})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;
    @Version
    @Column(name = "version", nullable = false)
    private long version;
    @Column(name = "business_key", nullable = false, unique = true)
    @Convert("uuidConverter")
    @TypeConverter(name = "uuidConverter", dataType = Object.class, objectType = UUID.class)
    private UUID businessKey;
    @Column(name = "login", nullable = false, unique = true, length = 32)
    private String login;
    @Column(name = "password", nullable = false, length = 64)
    private String password;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Column(name = "email", nullable = false, unique = true, length = 64)
    private String email;
    @Column(name = "locked", nullable = false)
    private boolean locked;
    @Column(name = "activated", nullable = false)
    private boolean activated;
    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "last_valid_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastValidLogin;
    @Column(name = "last_invalid_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastInvalidLogin;
    @Column(name = "invalid_login_attempts", nullable = false)
    private int invalidLoginAttempts;
    @Column(name = "activation_code", nullable = false)
    //@Convert("uuidConverter")
    private String activationCode;
    @Column(name = "reset_password_code", nullable = false)
    @Convert("uuidConverter")
    private UUID resetPasswordCode;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<UserAccessLevel> userAccessLevels = new ArrayList<>();


    @Version
    @Column(name = "version", table = "user_details", nullable = false)
    private long versionUserDetails;
    @Column(name = "first_name", table = "user_details", nullable = false, length = 32)
    private String firstName;
    @Size(max = 32)
    @Column(name = "last_name", table = "user_details", nullable = false, length = 32)
    private String lastName;
    @Column(name = "phone_number", table = "user_details", nullable = false, length = 10)
    private String phoneNumber;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, long version, UUID businessKey, String login, String password, String email, boolean locked, boolean activated, Date created, int invalidLoginAttempts) {
        this.id = id;
        this.version = version;
        this.businessKey = businessKey;
        this.login = login;
        this.password = password;
        this.email = email;
        this.locked = locked;
        this.activated = activated;
        this.created = created;
        this.invalidLoginAttempts = invalidLoginAttempts;
    }

    public Long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public UUID getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(UUID businessKey) {
        this.businessKey = businessKey;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastValidLogin() {
        return lastValidLogin;
    }

    public void setLastValidLogin(Date lastValidLogin) {
        this.lastValidLogin = lastValidLogin;
    }

    public Date getLastInvalidLogin() {
        return lastInvalidLogin;
    }

    public void setLastInvalidLogin(Date lastInvalidLogin) {
        this.lastInvalidLogin = lastInvalidLogin;
    }

    public int getInvalidLoginAttempts() {
        return invalidLoginAttempts;
    }

    public void setInvalidLoginAttempts(int invalidLoginAttempts) {
        this.invalidLoginAttempts = invalidLoginAttempts;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public UUID getResetPasswordCode() {
        return resetPasswordCode;
    }

    public void setResetPasswordCode(UUID resetPasswordCode) {
        this.resetPasswordCode = resetPasswordCode;
    }

    public Collection<UserAccessLevel> getUserAccessLevels() {
        return userAccessLevels;
    }

    public void setUserAccessLevels(Collection<UserAccessLevel> userAccessLevelCollection) {
        this.userAccessLevels = userAccessLevelCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isActivated() {
        return activated;
    }

    public long getVersionUserDetails() {
        return versionUserDetails;
    }

    public void setVersionUserDetails(long version_user_details) {
        this.versionUserDetails = version_user_details;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.User[ id=" + id + " ]";
    }

}
