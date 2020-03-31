/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "port")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Port.findAll", query = "SELECT p FROM Port p"),
    @NamedQuery(name = "Port.findById", query = "SELECT p FROM Port p WHERE p.id = :id"),
    @NamedQuery(name = "Port.findByVersion", query = "SELECT p FROM Port p WHERE p.version = :version"),
    @NamedQuery(name = "Port.findByName", query = "SELECT p FROM Port p WHERE p.name = :name"),
    @NamedQuery(name = "Port.findByLake", query = "SELECT p FROM Port p WHERE p.lake = :lake"),
    @NamedQuery(name = "Port.findByNearestCity", query = "SELECT p FROM Port p WHERE p.nearestCity = :nearestCity"),
    @NamedQuery(name = "Port.findByLong1", query = "SELECT p FROM Port p WHERE p.long1 = :long1"),
    @NamedQuery(name = "Port.findByLat", query = "SELECT p FROM Port p WHERE p.lat = :lat")})
public class Port implements Serializable {

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
    @Size(min = 1, max = 64)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "lake")
    private String lake;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "nearest_city")
    private String nearestCity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "long")
    private BigDecimal long1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lat")
    private BigDecimal lat;
    @OneToMany(mappedBy = "currentPortId")
    private Collection<Yacht> yachtCollection;

    public Port() {
    }

    public Port(Long id) {
        this.id = id;
    }

    public Port(Long id, long version, Object businessKey, String name, String lake, String nearestCity, BigDecimal long1, BigDecimal lat) {
        this.id = id;
        this.version = version;
        this.businessKey = businessKey;
        this.name = name;
        this.lake = lake;
        this.nearestCity = nearestCity;
        this.long1 = long1;
        this.lat = lat;
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

    public String getLake() {
        return lake;
    }

    public void setLake(String lake) {
        this.lake = lake;
    }

    public String getNearestCity() {
        return nearestCity;
    }

    public void setNearestCity(String nearestCity) {
        this.nearestCity = nearestCity;
    }

    public BigDecimal getLong1() {
        return long1;
    }

    public void setLong1(BigDecimal long1) {
        this.long1 = long1;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    @XmlTransient
    public Collection<Yacht> getYachtCollection() {
        return yachtCollection;
    }

    public void setYachtCollection(Collection<Yacht> yachtCollection) {
        this.yachtCollection = yachtCollection;
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
        if (!(object instanceof Port)) {
            return false;
        }
        Port other = (Port) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.Port[ id=" + id + " ]";
    }
    
}
