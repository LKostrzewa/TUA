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
@Table(name = "image")
@NamedQueries({
        @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i"),
        @NamedQuery(name = "Image.findById", query = "SELECT i FROM Image i WHERE i.id = :id"),
        @NamedQuery(name = "Image.findByVersion", query = "SELECT i FROM Image i WHERE i.version = :version"),
        @NamedQuery(name = "Image.findByLob", query = "SELECT i FROM Image i WHERE i.lob = :lob")})
public class Image implements Serializable {

    @Id
    @SequenceGenerator(name="ImageSeqGen",sequenceName="image_id_seq",allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ImageSeqGen")
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;
    @Version
    @Column(name = "version", nullable = false)
    private long version;
    @Convert("uuidConverter")
    @Column(name = "business_key", nullable = false, unique = true, updatable = false)
    private UUID businessKey;
    @Column(name = "lob", nullable = false, updatable = false)
    private byte[] lob;
    @JoinColumn(name = "yacht_model_id", referencedColumnName = "id", nullable = false, updatable = false)
    @ManyToOne(optional = false)
    private YachtModel yachtModel;

    public Image() {
    }

    public Image(byte[] lob, YachtModel yachtModel) {
        this.lob = lob;
        this.yachtModel = yachtModel;

        this.businessKey = UUID.randomUUID();
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

    public byte[] getLob() {
        return lob;
    }

    public YachtModel getYachtModel() {
        return yachtModel;
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
        if (!(object instanceof Image)) {
            return false;
        }
        Image other = (Image) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.Image[ id=" + id
                + ", version=" + version + " ]";
    }
}
