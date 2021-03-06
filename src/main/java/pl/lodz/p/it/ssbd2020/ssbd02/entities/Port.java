/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.TypeConverter;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
        @NamedQuery(name = "Port.findByLat", query = "SELECT p FROM Port p WHERE p.lat = :lat"),
        @NamedQuery(name = "Port.countByName", query = "SELECT COUNT(p) FROM Port p WHERE p.name = :name"),
        @NamedQuery(name = "Port.findByActive", query = "SELECT p FROM Port p WHERE p.active = TRUE ")})

public class Port implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;
    @Version
    @Column(name = "version", nullable = false)
    @NotNull
    private long version;
    @Column(name = "business_key", nullable = false, unique = true, updatable = false)
    @NotNull
    private String businessKey;
    @Column(name = "name", nullable = false, unique = true, length = 64)
    @NotNull
    @Size(max = 64)
    private String name;
    @Column(name = "lake", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String lake;
    @Column(name = "nearest_city", length = 32, nullable = false)
    @NotNull
    @Size(max = 32)
    private String nearestCity;
    @Column(name = "longitude", nullable = false)
    @NotNull
    @Digits(integer = 3,fraction = 6)
    @Min(0)
    private BigDecimal long1;
    @Column(name = "latitude", nullable = false)
    @NotNull
    @Digits(integer = 3,fraction = 6)
    @Min(0)
    private BigDecimal lat;
    @Column(name = "active", nullable = false)
    @NotNull
    private boolean active;
    @OneToMany(mappedBy = "currentPort")
    private Collection<Yacht> yachts = new ArrayList<>();

    public Port() {
    }

    public Port(String name, String lake, String nearestCity, BigDecimal long1, BigDecimal lat) {
        this.name = name;
        this.lake = lake;
        this.nearestCity = nearestCity;
        this.long1 = long1;
        this.lat = lat;

        this.businessKey = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public String getBusinessKey() {
        return businessKey;
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
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.Port[ id=" + id
                + ", version=" + version + " ]";
    }
}
