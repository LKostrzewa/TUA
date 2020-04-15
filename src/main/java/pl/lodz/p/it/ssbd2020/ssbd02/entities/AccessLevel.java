/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import org.eclipse.persistence.annotations.Convert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "access_level")
@NamedQueries({
        @NamedQuery(name = "AccessLevel.findAll", query = "SELECT a FROM AccessLevel a"),
        @NamedQuery(name = "AccessLevel.findById", query = "SELECT a FROM AccessLevel a WHERE a.id = :id"),
        @NamedQuery(name = "AccessLevel.findByVersion", query = "SELECT a FROM AccessLevel a WHERE a.version = :version"),
        @NamedQuery(name = "AccessLevel.findByName", query = "SELECT a FROM AccessLevel a WHERE a.name = :name")})
public class AccessLevel implements Serializable {

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
    @Column(name = "name", nullable = false, length = 32)
    private String name;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "accessLevel")
    private Collection<UserAccessLevel> userAccessLevels = new ArrayList<>();
    /*@ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "accessLevels")
    Collection<User> users = new ArrayList<>();*/


    public AccessLevel() {
    }

    public AccessLevel(Long id) {
        this.id = id;
    }

    public AccessLevel(Long id, long version, UUID businessKey, String name) {
        this.id = id;
        this.version = version;
        this.businessKey = businessKey;
        this.name = name;
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

    public void setBusinessKey(UUID businessKey) {
        this.businessKey = businessKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }*/

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
        if (!(object instanceof AccessLevel)) {
            return false;
        }
        AccessLevel other = (AccessLevel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.AccessLevel[ id=" + id + " ]";
    }

}
