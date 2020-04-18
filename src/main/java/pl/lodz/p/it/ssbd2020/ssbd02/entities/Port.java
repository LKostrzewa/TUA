/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import org.eclipse.persistence.annotations.Convert;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "port")
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
    @Column(name = "name", nullable = false, unique = true, length = 64)
    private String name;
    @Column(name = "lake", nullable = false, length = 32)
    private String lake;
    @Column(name = "nearest_city", length = 32, nullable = false)
    private String nearestCity;
    @Column(name = "long", nullable = false)
    private BigDecimal long1;
    @Column(name = "lat", nullable = false)
    private BigDecimal lat;
    @Column(name = "active", nullable = false)
    private boolean active;
    @OneToMany(mappedBy = "currentPort")
    private Collection<Yacht> yachts = new ArrayList<>();

    public Port() {
    }

    public Port(Long id) {
        this.id = id;
    }

    public Port(Long id, long version, UUID businessKey, String name, String lake, String nearestCity, BigDecimal long1, BigDecimal lat) {
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

    public long getVersion() {
        return version;
    }

    public UUID getBusinessKey() {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Collection<Yacht> getYachts() {
        return yachts;
    }

    public void setYachts(Collection<Yacht> yachtCollection) {
        this.yachts = yachtCollection;
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
