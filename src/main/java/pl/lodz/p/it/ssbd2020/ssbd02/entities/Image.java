/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author Lukasz
 */
@Entity
@Table(name = "image")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i"),
        @NamedQuery(name = "Image.findById", query = "SELECT i FROM Image i WHERE i.id = :id"),
        @NamedQuery(name = "Image.findByVersion", query = "SELECT i FROM Image i WHERE i.version = :version"),
        @NamedQuery(name = "Image.findByLob", query = "SELECT i FROM Image i WHERE i.lob = :lob")})
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "version")
    private long version;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "business_key")
    private UUID businessKey;
    @Column(name = "lob")
    private Serializable lob;
    @JoinColumn(name = "yacht_model_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private YachtModel yachtModelId;

    public Image() {
    }

    public Image(Long id) {
        this.id = id;
    }

    public Image(Long id, long version, UUID businessKey) {
        this.id = id;
        this.version = version;
        this.businessKey = businessKey;
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

    public Serializable getLob() {
        return lob;
    }

    public void setLob(Serializable lob) {
        this.lob = lob;
    }

    public YachtModel getYachtModelId() {
        return yachtModelId;
    }

    public void setYachtModelId(YachtModel yachtModelId) {
        this.yachtModelId = yachtModelId;
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
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.Image[ id=" + id + " ]";
    }
}
