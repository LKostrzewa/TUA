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
@Table(name = "yacht")
@NamedQueries({
        @NamedQuery(name = "Yacht.findAll", query = "SELECT y FROM Yacht y"),
        @NamedQuery(name = "Yacht.findById", query = "SELECT y FROM Yacht y WHERE y.id = :id"),
        @NamedQuery(name = "Yacht.findByVersion", query = "SELECT y FROM Yacht y WHERE y.version = :version"),
        @NamedQuery(name = "Yacht.findByName", query = "SELECT y FROM Yacht y WHERE y.name = :name"),
        @NamedQuery(name = "Yacht.findByProductionYear", query = "SELECT y FROM Yacht y WHERE y.productionYear = :productionYear"),
        @NamedQuery(name = "Yacht.findByPriceMultiplier", query = "SELECT y FROM Yacht y WHERE y.priceMultiplier = :priceMultipler"),
        @NamedQuery(name = "Yacht.findByCondition", query = "SELECT y FROM Yacht y WHERE y.equipment = :condition"),
        @NamedQuery(name = "Yacht.findByAvgRating", query = "SELECT y FROM Yacht y WHERE y.avgRating = :avgRating")})
public class Yacht implements Serializable {

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
    @Column(name = "production_year", nullable = false)
    private Integer productionYear;
    @Column(name = "price_multiplier", nullable = false)
    private BigDecimal priceMultiplier;
    @Column(name = "equipment", nullable = false, length = 2048)
    private String equipment;
    @Column(name = "avg_rating")
    private BigDecimal avgRating;
    @Column(name = "active", nullable = false)
    private boolean active;
    @JoinColumn(name = "current_port_id", referencedColumnName = "id")
    @ManyToOne
    private Port currentPort;
    @JoinColumn(name = "yacht_model_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private YachtModel yachtModel;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "yacht")
    private Collection<Rental> rentals = new ArrayList<>();

    public Yacht() {
    }

    public Yacht(Long id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
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

    public void setYachtModel(YachtModel yachtModelId) {
        this.yachtModel = yachtModelId;
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
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.Yacht[ id=" + id + " ]";
    }

}
