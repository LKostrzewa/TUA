/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import org.eclipse.persistence.annotations.Convert;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "opinion")
@NamedQueries({
        @NamedQuery(name = "Opinion.findAll", query = "SELECT o FROM Opinion o"),
        @NamedQuery(name = "Opinion.findById", query = "SELECT o FROM Opinion o WHERE o.id = :id"),
        @NamedQuery(name = "Opinion.findByVersion", query = "SELECT o FROM Opinion o WHERE o.version = :version"),
        @NamedQuery(name = "Opinion.findByRating", query = "SELECT o FROM Opinion o WHERE o.rating = :rating"),
        @NamedQuery(name = "Opinion.findByComment", query = "SELECT o FROM Opinion o WHERE o.comment = :comment"),
        @NamedQuery(name = "Opinion.findByDate", query = "SELECT o FROM Opinion o WHERE o.date = :date")})
public class Opinion implements Serializable {

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
    @Min(1)
    @Max(5)
    @Column(name = "rating", nullable = false)
    private int rating;
    @Column(name = "comment", length = 1024)
    private String comment;
    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "edited", nullable = false)
    private boolean edited;
    @JoinColumn(name = "rental_id", referencedColumnName = "id", updatable = false)
    @OneToOne(optional = false)
    private Rental rental;

    public Opinion() {
    }

    public Opinion(Long id) {
        this.id = id;
    }

    public Opinion(Long id, long version, UUID businessKey, int rating, Date date) {
        this.id = id;
        this.version = version;
        this.businessKey = businessKey;
        this.rating = rating;
        this.date = date;
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

    public void setBusinessKey(UUID businessKey) {
        this.businessKey = businessKey;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Rental getRental() {
        return rental;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
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
        if (!(object instanceof Opinion)) {
            return false;
        }
        Opinion other = (Opinion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.Opinion[ id=" + id + " ]";
    }

}
