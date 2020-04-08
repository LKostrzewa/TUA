/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import org.eclipse.persistence.annotations.TypeConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lukasz
 */
@Entity
@Table(name = "access_level")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessLevel.findAll", query = "SELECT a FROM AccessLevel a"),
    @NamedQuery(name = "AccessLevel.findById", query = "SELECT a FROM AccessLevel a WHERE a.id = :id"),
    @NamedQuery(name = "AccessLevel.findByVersion", query = "SELECT a FROM AccessLevel a WHERE a.version = :version"),
    @NamedQuery(name = "AccessLevel.findByName", query = "SELECT a FROM AccessLevel a WHERE a.name = :name")})
public class AccessLevel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "access_level_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
//    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "version")
    private long version;
//    @Basic(optional = false)
//    @NotNull
    @Lob
    @Column(name = "business_key")
    @org.eclipse.persistence.annotations.Convert("uuidConverter")
    @TypeConverter(name = "uuidConverter", dataType = Object.class, objectType = UUID.class)
    private UUID businessKey;
//    @Basic(optional = false)
//    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accessLevelId")
    private Collection<UserAccessLevel> userAccessLevelCollection = new ArrayList<>();

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

//    @XmlTransient
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
