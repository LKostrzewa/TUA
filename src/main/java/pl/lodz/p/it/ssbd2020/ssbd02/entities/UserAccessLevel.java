/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import org.eclipse.persistence.annotations.TypeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author Lukasz
 */
@Entity
@Table(name = "user_access_level", uniqueConstraints = @UniqueConstraint(columnNames = {"access_level_id", "user_id"}))
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "UserAccessLevel.findAll", query = "SELECT u FROM UserAccessLevel u"),
        @NamedQuery(name = "UserAccessLevel.findById", query = "SELECT u FROM UserAccessLevel u WHERE u.id = :id"),
        @NamedQuery(name = "UserAccessLevel.findByVersion", query = "SELECT u FROM UserAccessLevel u WHERE u.version = :version")})
public class UserAccessLevel implements Serializable {

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
    @org.eclipse.persistence.annotations.Convert("uuidConverter")
    @TypeConverter(name = "uuidConverter", dataType = Object.class, objectType = UUID.class)
    private UUID businessKey;
    @JoinColumn(name = "access_level_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AccessLevel accessLevelId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userId;

    public UserAccessLevel() {
    }

    public UserAccessLevel(Long id) {
        this.id = id;
    }

    public UserAccessLevel(Long id, long version, UUID businessKey) {
        this.id = id;
        this.version = version;
        this.businessKey = businessKey;
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

    public AccessLevel getAccessLevelId() {
        return accessLevelId;
    }

    public void setAccessLevelId(AccessLevel accessLevelId) {
        this.accessLevelId = accessLevelId;
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
        if (!(object instanceof UserAccessLevel)) {
            return false;
        }
        UserAccessLevel other = (UserAccessLevel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel[ id=" + id + " ]";
    }
}
