/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.TypeConverter;


/**
 *
 * @author Lukasz
 */
@Entity
@Table(name = "\"user\"")
@SecondaryTable(name="user_details", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
@XmlRootElement
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
    @NamedQuery(name = "User.findByInvalidLoginAttemps", query = "SELECT u FROM User u WHERE u.invalidLoginAttemps = :invalidLoginAttemps")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "version")
    private long version;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "business_key")
    @Convert("uuidConverter")
    @TypeConverter(name = "uuidConverter", dataType = Object.class, objectType = UUID.class)
    private UUID businessKey;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "password")
    private String password;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "locked")
    private boolean locked;
    @Basic(optional = false)
    @NotNull
    @Column(name = "activated")
    private boolean activated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "last_valid_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastValidLogin;
    @Column(name = "last_invalid_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastInvalidLogin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "invalid_login_attemps")
    private int invalidLoginAttemps;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "activation_code")
    @Convert("uuidConverter")
    private UUID activationCode;
    @Lob
    @Column(name = "reset_password_code")
    @Convert("uuidConverter")
    private UUID resetPasswordCode;





//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
//    private Collection<UserDetails> userDetailsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<UserAccessLevel> userAccessLevelCollection = new ArrayList<>();










    @Basic(optional = false)
    @NotNull
    @Column(name = "version", table = "user_details")
    private long version_user_details;




    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "business_key", table = "user_details")
    @Convert("uuidConverter")
    private UUID businessKey_user_details;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "first_name", table = "user_details")
    private String firstName;
    @Size(max = 32)
    @Column(name = "last_name", table = "user_details")
    private String lastName;
    @Size(max = 10)
    @Column(name = "phone_number", table = "user_details")
    private String phoneNumber;

    public User() {
    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, long version, UUID businessKey, String login, String password, String email, boolean locked, boolean activated, Date created, int invalidLoginAttemps) {
        this.id = id;
        this.version = version;
        this.businessKey = businessKey;
        this.login = login;
        this.password = password;
        this.email = email;
        this.locked = locked;
        this.activated = activated;
        this.created = created;
        this.invalidLoginAttemps = invalidLoginAttemps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public UUID getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(UUID businessKey) {
        this.businessKey = businessKey;
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

    public int getInvalidLoginAttemps() {
        return invalidLoginAttemps;
    }

    public void setInvalidLoginAttemps(int invalidLoginAttemps) {
        this.invalidLoginAttemps = invalidLoginAttemps;
    }

    public UUID getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(UUID activationCode) {
        this.activationCode = activationCode;
    }

    public UUID getResetPasswordCode() {
        return resetPasswordCode;
    }

    public void setResetPasswordCode(UUID resetPasswordCode) {
        this.resetPasswordCode = resetPasswordCode;
    }

    //    @XmlTransient
//    public Collection<UserDetails> getUserDetailsCollection() {
//        return userDetailsCollection;
//    }
//
//    public void setUserDetailsCollection(Collection<UserDetails> userDetailsCollection) {
//        this.userDetailsCollection = userDetailsCollection;
//    }

    //@XmlTransient
    public Collection<UserAccessLevel> getUserAccessLevelCollection() {
        return userAccessLevelCollection;
    }

    public void setUserAccessLevelCollection(Collection<UserAccessLevel> userAccessLevelCollection) {
        this.userAccessLevelCollection = userAccessLevelCollection;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isActivated() {
        return activated;
    }

    public long getVersion_user_details() {
        return version_user_details;
    }

    public void setVersion_user_details(long version_user_details) {
        this.version_user_details = version_user_details;
    }

    public UUID getBusinessKey_user_details() {
        return businessKey_user_details;
    }

    public void setBusinessKey_user_details(UUID businessKey_user_details) {
        this.businessKey_user_details = businessKey_user_details;
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
