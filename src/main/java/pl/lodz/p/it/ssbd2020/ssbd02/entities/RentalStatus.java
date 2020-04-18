/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import org.eclipse.persistence.annotations.Convert;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "rental_status")
@NamedQueries({
        @NamedQuery(name = "RentalStatus.findAll", query = "SELECT r FROM RentalStatus r"),
        @NamedQuery(name = "RentalStatus.findById", query = "SELECT r FROM RentalStatus r WHERE r.id = :id"),
        @NamedQuery(name = "RentalStatus.findByVersion", query = "SELECT r FROM RentalStatus r WHERE r.version = :version"),
        @NamedQuery(name = "RentalStatus.findByName", query = "SELECT r FROM RentalStatus r WHERE r.name = :name")})
public class RentalStatus implements Serializable {

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
    @Column(name = "name", nullable = false, unique = true, length = 32)
    private String name;
    /*@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "rentalStatusId")
    private Collection<Rental> rentalCollection;*/

    public RentalStatus() {
    }

    public RentalStatus(Long id) {
        this.id = id;
    }

    public RentalStatus(Long id, long version, UUID businessKey, String name) {
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

    //@XmlTransient
    /*public Collection<Rental> getRentalCollection() {
        return rentalCollection;
    }

    public void setRentalCollection(Collection<Rental> rentalCollection) {
        this.rentalCollection = rentalCollection;
    }*/

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RentalStatus)) {
            return false;
        }
        RentalStatus other = (RentalStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.RentalStatus[ id=" + id + " ]";
    }

}
