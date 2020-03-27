/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lukasz
 */
@Entity
@Table(name = "user_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserDetails.findAll", query = "SELECT u FROM UserDetails u"),
    @NamedQuery(name = "UserDetails.findById", query = "SELECT u FROM UserDetails u WHERE u.id = :id"),
    @NamedQuery(name = "UserDetails.findByVersion", query = "SELECT u FROM UserDetails u WHERE u.version = :version"),
    @NamedQuery(name = "UserDetails.findByFirstName", query = "SELECT u FROM UserDetails u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "UserDetails.findByLastName", query = "SELECT u FROM UserDetails u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "UserDetails.findByPhoneNumber", query = "SELECT u FROM UserDetails u WHERE u.phoneNumber = :phoneNumber")})
public class UserDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "business_key")
    private Object businessKey;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "first_name")
    private String firstName;
    @Size(max = 32)
    @Column(name = "last_name")
    private String lastName;
    @Size(max = 10)
    @Column(name = "phone_number")
    private String phoneNumber;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public UserDetails() {
    }

    public UserDetails(Long id) {
        this.id = id;
    }

    public UserDetails(Long id, long version, Object businessKey, String firstName) {
        this.id = id;
        this.version = version;
        this.businessKey = businessKey;
        this.firstName = firstName;
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

    public Object getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(Object businessKey) {
        this.businessKey = businessKey;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof UserDetails)) {
            return false;
        }
        UserDetails other = (UserDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.UserDetails[ id=" + id + " ]";
    }
    
}
