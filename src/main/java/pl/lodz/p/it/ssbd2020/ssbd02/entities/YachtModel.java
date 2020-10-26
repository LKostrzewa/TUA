/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import org.eclipse.persistence.annotations.Convert;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.DefaultValue;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "yacht_model")
@NamedQueries({
        @NamedQuery(name = "YachtModel.findAll", query = "SELECT y FROM YachtModel y"),
        @NamedQuery(name = "YachtModel.findById", query = "SELECT y FROM YachtModel y WHERE y.id = :id"),
        @NamedQuery(name = "YachtModel.findByVersion", query = "SELECT y FROM YachtModel y WHERE y.version = :version"),
        @NamedQuery(name = "YachtModel.findByManufacturer", query = "SELECT y FROM YachtModel y WHERE y.manufacturer = :manufacturer"),
        @NamedQuery(name = "YachtModel.findByModel", query = "SELECT y FROM YachtModel y WHERE y.model = :model"),
        @NamedQuery(name = "YachtModel.findByCapacity", query = "SELECT y FROM YachtModel y WHERE y.capacity = :capacity"),
        @NamedQuery(name = "YachtModel.findByGeneralDescription", query = "SELECT y FROM YachtModel y WHERE y.generalDescription = :generalDescription"),
        @NamedQuery(name = "YachtModel.findByBasicPrice", query = "SELECT y FROM YachtModel y WHERE y.basicPrice = :basicPrice"),
        @NamedQuery(name = "YachtModel.countByModel", query = "SELECT COUNT(y) FROM YachtModel y WHERE y.model = :model")})
public class YachtModel implements Serializable {

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
    @Column(name = "manufacturer", nullable = false, length = 32)
    @NotNull
    @Size(max = 32)
    private String manufacturer;
    @Column(name = "model", nullable = false, unique = true, length = 32)
    @NotNull
    @Size(max = 32)
    private String model;
    @Column(name = "capacity", nullable = false)
    @NotNull
    private int capacity;
    @Column(name = "general_description", nullable = false, length = 4096)
    @NotNull
    @Size(max = 4096)
    private String generalDescription;
    @Column(name = "basic_price", nullable = false)
    @NotNull
    @Digits(integer = 8, fraction = 2)
    private BigDecimal basicPrice;
    @Column(name = "active", nullable = false)
    @NotNull
    private boolean active;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "yachtModel")
    private Collection<Image> images = new ArrayList<>();

    public YachtModel() {
    }

    public YachtModel(String manufacturer, String model, int capacity, String generalDescription, BigDecimal basicPrice) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.capacity = capacity;
        this.generalDescription = generalDescription;
        this.basicPrice = basicPrice;

        this.active = true;
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

    public String getManufacturer() {
        return manufacturer;
    }


    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }

    public BigDecimal getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(BigDecimal basicPrice) {
        this.basicPrice = basicPrice;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }



    public Collection<Image> getImages() {
        return images;
    }

    public void setImages(Collection<Image> imageCollection) {
        this.images = imageCollection;
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
        if (!(object instanceof YachtModel)) {
            return false;
        }
        YachtModel other = (YachtModel) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel[ id=" + id
                + ", version=" + version + " ]";
    }
}
