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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "yacht")
@NamedQueries({
        @NamedQuery(name = "Yacht.findAll", query = "SELECT y FROM Yacht y"),
        @NamedQuery(name = "Yacht.findById", query = "SELECT y FROM Yacht y WHERE y.id = :id"),
        @NamedQuery(name = "Yacht.findByVersion", query = "SELECT y FROM Yacht y WHERE y.version = :version"),
        @NamedQuery(name = "Yacht.findByName", query = "SELECT y FROM Yacht y WHERE y.name = :name"),
        @NamedQuery(name = "Yacht.findByProductionYear", query = "SELECT y FROM Yacht y WHERE y.productionYear = :productionYear"),
        @NamedQuery(name = "Yacht.findByPriceMultiplier", query = "SELECT y FROM Yacht y WHERE y.priceMultiplier = :priceMultipler"),
        @NamedQuery(name = "Yacht.findByCondition", query = "SELECT y FROM Yacht y WHERE y.equipment = :condition"),
        @NamedQuery(name = "Yacht.findByAvgRating", query = "SELECT y FROM Yacht y WHERE y.avgRating = :avgRating"),
        @NamedQuery(name = "Yacht.countByName", query = "SELECT COUNT(y) FROM Yacht y WHERE y.name = :name"),})
public class Yacht implements Serializable {

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
    @Column(name = "name", nullable = false, unique = true, length = 32)
    @NotNull
    @Size(max = 32)
    private String name;
    @Column(name = "production_year", nullable = false, updatable = false)
    @NotNull
    private Integer productionYear;
    @Column(name = "price_multiplier", nullable = false)
    @NotNull
    @Digits(integer = 1,fraction = 2)
    private BigDecimal priceMultiplier;
    @Column(name = "equipment", nullable = false, length = 2048)
    @NotNull
    @Size(max = 2048)
    private String equipment;
    @Column(name = "avg_rating")
    @Digits(integer = 1,fraction = 2)
    private BigDecimal avgRating;
    @Column(name = "active", nullable = false)
    @NotNull
    private boolean active = true;
    @JoinColumn(name = "current_port_id", referencedColumnName = "id")
    @ManyToOne
    private Port currentPort;
    @JoinColumn(name = "yacht_model_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    @NotNull
    private YachtModel yachtModel;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "yacht")
    private Collection<Rental> rentals = new ArrayList<>();

    public Yacht() {
    }

    public Yacht(String name, Integer productionYear, BigDecimal priceMultiplier, String equipment, YachtModel yachtModel) {
        this.name = name;
        this.productionYear = productionYear;
        this.priceMultiplier = priceMultiplier;
        this.equipment = equipment;
        this.yachtModel = yachtModel;

        this.businessKey = UUID.randomUUID().toString();
    }

    public Yacht(String yachtName) {
        this.name = yachtName;
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

    public Integer getProductionYear() {
        return productionYear;
    }

    public BigDecimal getPriceMultiplier() {
        return priceMultiplier;
    }

    public void setPriceMultiplier(BigDecimal priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public BigDecimal getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(BigDecimal avgRating) {
        this.avgRating = avgRating;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(Port currentPortId) {
        this.currentPort = currentPortId;
    }

    public YachtModel getYachtModel() {
        return yachtModel;
    }

    public Collection<Rental> getRentals() {
        return rentals;
    }

    public void setRentals(Collection<Rental> rentalCollection) {
        this.rentals = rentalCollection;
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
        if (!(object instanceof Yacht)) {
            return false;
        }
        Yacht other = (Yacht) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht[ id=" + id
                + ", version=" + version + " ]";
    }
}
