/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import org.eclipse.persistence.annotations.Convert;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Lukasz
 */
@Entity
@Table(name = "yacht_model")
//@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "YachtModel.findAll", query = "SELECT y FROM YachtModel y"),
    @NamedQuery(name = "YachtModel.findById", query = "SELECT y FROM YachtModel y WHERE y.id = :id"),
    @NamedQuery(name = "YachtModel.findByVersion", query = "SELECT y FROM YachtModel y WHERE y.version = :version"),
    @NamedQuery(name = "YachtModel.findByManufacturer", query = "SELECT y FROM YachtModel y WHERE y.manufacturer = :manufacturer"),
    @NamedQuery(name = "YachtModel.findByModel", query = "SELECT y FROM YachtModel y WHERE y.model = :model"),
    @NamedQuery(name = "YachtModel.findByCapacity", query = "SELECT y FROM YachtModel y WHERE y.capacity = :capacity"),
    @NamedQuery(name = "YachtModel.findByGeneralDescription", query = "SELECT y FROM YachtModel y WHERE y.generalDescription = :generalDescription"),
    @NamedQuery(name = "YachtModel.findByBasicPrice", query = "SELECT y FROM YachtModel y WHERE y.basicPrice = :basicPrice")})
public class YachtModel implements Serializable {

    //private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Version
    @Column(name = "version")
    private long version;
    @Basic(optional = false)
    @NotNull
    //@Lob
    @Column(name = "business_key")
    @Convert("uuidConverter")
    private UUID businessKey;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "manufacturer")
    private String manufacturer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "model")
    private String model;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capacity")
    private int capacity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4096)
    @Column(name = "general_description")
    private String generalDescription;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "basic_price")
    private BigDecimal basicPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "active")
    private boolean active;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "yachtModelId")
    private Collection<Image> imageCollection;
    @OneToMany(cascade = CascadeType.REFRESH, mappedBy = "yachtModelId")
    private Collection<Yacht> yachtCollection;

    public YachtModel() {
    }

    public YachtModel(Long id) {
        this.id = id;
    }

    public YachtModel(Long id, long version, UUID businessKey, String manufacturer, String model, int capacity, String generalDescription, BigDecimal basicPrice) {
        this.id = id;
        this.version = version;
        this.businessKey = businessKey;
        this.manufacturer = manufacturer;
        this.model = model;
        this.capacity = capacity;
        this.generalDescription = generalDescription;
        this.basicPrice = basicPrice;
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

    //@XmlTransient
    public Collection<Image> getImageCollection() {
        return imageCollection;
    }

    public void setImageCollection(Collection<Image> imageCollection) {
        this.imageCollection = imageCollection;
    }

    //@XmlTransient
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
        if (!(object instanceof YachtModel)) {
            return false;
        }
        YachtModel other = (YachtModel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.YachtModel[ id=" + id + " ]";
    }
    
}
