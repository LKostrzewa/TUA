/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lukasz
 */
@Entity
@Table(name = "yacht")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Yacht.findAll", query = "SELECT y FROM Yacht y"),
    @NamedQuery(name = "Yacht.findById", query = "SELECT y FROM Yacht y WHERE y.id = :id"),
    @NamedQuery(name = "Yacht.findByVersion", query = "SELECT y FROM Yacht y WHERE y.version = :version"),
    @NamedQuery(name = "Yacht.findByName", query = "SELECT y FROM Yacht y WHERE y.name = :name"),
    @NamedQuery(name = "Yacht.findByProductionYear", query = "SELECT y FROM Yacht y WHERE y.productionYear = :productionYear"),
    @NamedQuery(name = "Yacht.findByPriceMultipler", query = "SELECT y FROM Yacht y WHERE y.priceMultiplier = :priceMultipler"),
    @NamedQuery(name = "Yacht.findByCondition", query = "SELECT y FROM Yacht y WHERE y.equipment = :condition"),
    @NamedQuery(name = "Yacht.findByAvgRating", query = "SELECT y FROM Yacht y WHERE y.avgRating = :avgRating")})
public class Yacht implements Serializable {

    //private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "version")
    private BigInteger version;
    @Lob
    @Column(name = "business_key")
    private UUID businessKey;
    @Size(max = 32)
    @Column(name = "name")
    private String name;
    @Column(name = "production_year")
    private Integer productionYear;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price_multiplier")
    private BigDecimal priceMultiplier;
    @Size(max = 2048)
    @Column(name = "equipment")
    private String equipment;
    @Column(name = "avg_rating")
    private BigDecimal avgRating;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @JoinColumn(name = "current_port_id", referencedColumnName = "id")
    @ManyToOne
    private Port currentPortId;
    @JoinColumn(name = "yacht_model_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private YachtModel yachtModelId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "yachtId")
    private Collection<Rental> rentalCollection;

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

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
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

    public Port getCurrentPortId() {
        return currentPortId;
    }

    public void setCurrentPortId(Port currentPortId) {
        this.currentPortId = currentPortId;
    }

    public YachtModel getYachtModelId() {
        return yachtModelId;
    }

    public void setYachtModelId(YachtModel yachtModelId) {
        this.yachtModelId = yachtModelId;
    }

    //@XmlTransient
    public Collection<Rental> getRentalCollection() {
        return rentalCollection;
    }

    public void setRentalCollection(Collection<Rental> rentalCollection) {
        this.rentalCollection = rentalCollection;
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
