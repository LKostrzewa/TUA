/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccessLevel.findAll", query = "SELECT a FROM AccessLevel a"),
    @NamedQuery(name = "AccessLevel.findById", query = "SELECT a FROM AccessLevel a WHERE a.id = :id"),
    @NamedQuery(name = "AccessLevel.findByVersion", query = "SELECT a FROM AccessLevel a WHERE a.version = :version"),
    @NamedQuery(name = "AccessLevel.findByName", query = "SELECT a FROM AccessLevel a WHERE a.name = :name")})
public class AccessLevel implements Serializable {

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
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accessLevelId")
    private Collection<UserAccessLevel> userAccessLevelCollection;

    public AccessLevel() {
    }

    public AccessLevel(Long id) {
        this.id = id;
    }

    public AccessLevel(Long id, long version, Object businessKey, String name) {
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

    public void setBusinessKey(Object businessKey) {
        this.businessKey = businessKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
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
