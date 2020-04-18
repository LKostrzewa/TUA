/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import org.eclipse.persistence.annotations.Convert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;


@Entity
@Table(name = "user_access_level", uniqueConstraints = @UniqueConstraint(columnNames = {"access_level_id", "user_id"}))
@NamedQueries({
        @NamedQuery(name = "UserAccessLevel.findAll", query = "SELECT u FROM UserAccessLevel u"),
        @NamedQuery(name = "UserAccessLevel.findById", query = "SELECT u FROM UserAccessLevel u WHERE u.id = :id"),
        @NamedQuery(name = "UserAccessLevel.findByVersion", query = "SELECT u FROM UserAccessLevel u WHERE u.version = :version")})
public class UserAccessLevel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;
    @Version
    @Column(name = "version", nullable = false)
    private long version;
    @Column(name = "business_key", nullable = false, unique = true)
    @Convert("uuidConverter")
    private UUID businessKey;
    @JoinColumn(name = "access_level_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AccessLevel accessLevel;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User user;

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

    public long getVersion() {
        return version;
    }

    public UUID getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(UUID businessKey) {
        this.businessKey = businessKey;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevelId) {
        this.accessLevel = accessLevelId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userId) {
        this.user = userId;
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
