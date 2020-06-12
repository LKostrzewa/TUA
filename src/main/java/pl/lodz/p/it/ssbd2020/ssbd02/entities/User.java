/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.TypeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
        @NamedQuery(name = "User.findByLogin", query = "SELECT u FROM User u WHERE u.login = :login"),
        @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
        @NamedQuery(name = "User.findByActivationCode", query = "SELECT u FROM User u WHERE u.activationCode = :activationCode"),
        @NamedQuery(name = "User.findByResetPasswordCode", query = "SELECT u FROM User u WHERE u.resetPasswordCode = :resetPasswordCode"),
        @NamedQuery(name = "User.countByLogin", query = "SELECT COUNT(u) FROM User u WHERE u.login = :login"),
        @NamedQuery(name = "User.countByActivationCode", query = "SELECT COUNT(u) FROM User u WHERE u.activationCode = :activationCode"),
        @NamedQuery(name = "User.countByEmail", query = "SELECT COUNT(u) FROM User u WHERE u.email = :email")})
public class User implements Serializable {

    @Id
    @SequenceGenerator(name = "UserSeqGen", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserSeqGen")
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @NotNull
    private Long id;
    @Version
    @Column(name = "version", nullable = false)
    @NotNull
    private long version;
    @Column(name = "business_key", nullable = false, unique = true, updatable = false)
    @Convert("uuidConverter")
    @TypeConverter(name = "uuidConverter", dataType = Object.class, objectType = UUID.class)
    @NotNull
    private UUID businessKey;
    @Column(name = "login", nullable = false, unique = true, length = 32)
    @NotNull
    @Size(min = 4, max = 32)
    @Pattern(regexp = "^[^=;%&'\"/\\\\]+$")
    private String login;
    @Column(name = "password", nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    private String password;
    @Pattern(regexp = "^[^\\s\\\\@]+@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\\.){1,11}[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?$")
    @Column(name = "email", nullable = false, unique = true, updatable = false, length = 64)
    @NotNull
    @Size(max = 64)
    private String email;
    @Column(name = "locked", nullable = false)
    @NotNull
    private boolean locked;
    @Column(name = "activated", nullable = false)
    @NotNull
    private boolean activated;
    @Column(name = "created", nullable = false, updatable = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "last_valid_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastValidLogin;
    @Column(name = "last_invalid_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastInvalidLogin;
    @Column(name = "invalid_login_attempts", nullable = false)
    @NotNull
    private int invalidLoginAttempts;
    @Column(name = "last_login_ip", length = 64)
    @Size(max = 64)
    private String lastLoginIp;
    @Column(name = "activation_code", nullable = false, unique = true, length = 128)
    @NotNull
    @Size(max = 128)
    private String activationCode;
    @Column(name = "reset_password_code", unique = true, length = 64)
    @Size(max = 64)
    private String resetPasswordCode;
    @Column(name = "reset_password_code_add_date")
    private Date resetPasswordCodeAddDate;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy = "user")
    private Collection<UserAccessLevel> userAccessLevels = new ArrayList<>();


    @Column(name = "first_name", table = "user_details", nullable = false, length = 32)
    @NotNull
    @Pattern(regexp = "[a-zA-ZąĄćĆęĘłŁńŃóÓśŚźŹżŻ.-]{2,31}")
    private String firstName;
    @Column(name = "last_name", table = "user_details", nullable = false, length = 32)
    @NotNull
    @Pattern(regexp = "[a-zA-ZąĄćĆęĘłŁńŃóÓśŚźŹżŻ.-]{2,31}")
    private String lastName;
    @Column(name = "phone_number", table = "user_details", nullable = false, length = 10)
    @NotNull
    @Pattern(regexp = "\\d{9}")
    private String phoneNumber;

    public User() {
    }

    public User(String login, String password, String email, String firstName, String lastName, String phoneNumber) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;

        this.businessKey = UUID.randomUUID();
        this.created = new Date();
    }

    public User(String userLogin) {
        this.login = userLogin;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public boolean getLocked() {
        return locked;
    }

    public boolean getActivated() {
        return activated;
    }

    public Date getCreated() {
        return created;
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

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getResetPasswordCode() {
        return resetPasswordCode;
    }

    public void setResetPasswordCode(String resetPasswordCode) {
        this.resetPasswordCode = resetPasswordCode;
    }

    public Date getResetPasswordCodeAddDate() {
        return resetPasswordCodeAddDate;
    }

    public void setResetPasswordCodeAddDate(Date resetPasswordCodeAddDate) {
        this.resetPasswordCodeAddDate = resetPasswordCodeAddDate;
    }

    public Collection<UserAccessLevel> getUserAccessLevels() {
        return userAccessLevels;
    }

    public void setUserAccessLevels(Collection<UserAccessLevel> userAccessLevelCollection) {
        this.userAccessLevels = userAccessLevelCollection;
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
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.User[ id=" + id
                + ", version=" + version + " ]";
    }
}
