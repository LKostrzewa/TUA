/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import org.eclipse.persistence.annotations.Convert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;


@Entity
@Table(name = "user_access_level", uniqueConstraints = @UniqueConstraint(columnNames = {"access_level_id", "user_id"}))
@NamedQueries({
        @NamedQuery(name = "UserAccessLevel.findAll", query = "SELECT u FROM UserAccessLevel u"),
        @NamedQuery(name = "UserAccessLevel.findById", query = "SELECT u FROM UserAccessLevel u WHERE u.id = :id")})
public class UserAccessLevel implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;
    @Version
    @Column(name = "version", nullable = false)
    @NotNull
    private long version;
    @JoinColumn(name = "access_level_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(optional = false)
    @NotNull
    private AccessLevel accessLevel;
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
    @ManyToOne(optional = false)
    @NotNull

    private User user;

    public UserAccessLevel() {
    }

    public UserAccessLevel(User user, AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public User getUser() {
        return user;
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
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.UserAccessLevel[ id=" + id
                + ", version=" + version + " ]";
    }
}
