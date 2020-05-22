/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import org.eclipse.persistence.annotations.Convert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @SequenceGenerator(name="RentalStatusSeqGen",sequenceName="rental_status_id_seq",allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RentalStatusSeqGen")
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    @NotNull
    private Long id;
    @Version
    @Column(name = "version", nullable = false)
    @NotNull
    private long version;
    @Column(name = "business_key", nullable = false, unique = true, updatable = false)
    @Convert("uuidConverter")
    @NotNull
    private UUID businessKey;
    @Column(name = "name", nullable = false, unique = true, updatable = false,length = 32)
    @NotNull
    @Size(max = 32)
    private String name;

    public RentalStatus() {
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

    public String getName() {
        return name;
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
        if (!(object instanceof RentalStatus)) {
            return false;
        }
        RentalStatus other = (RentalStatus) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.RentalStatus[ id=" + id
                + ", version=" + version + " ]";
    }
}
