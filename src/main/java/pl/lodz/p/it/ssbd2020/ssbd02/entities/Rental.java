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
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "rental")
@NamedQueries({
        @NamedQuery(name = "Rental.findAll", query = "SELECT r FROM Rental r"),
        @NamedQuery(name = "Rental.findById", query = "SELECT r FROM Rental r WHERE r.id = :id"),
        @NamedQuery(name = "Rental.findByVersion", query = "SELECT r FROM Rental r WHERE r.version = :version"),
        @NamedQuery(name = "Rental.findByBeginDate", query = "SELECT r FROM Rental r WHERE r.beginDate = :beginDate"),
        @NamedQuery(name = "Rental.findByEndDate", query = "SELECT r FROM Rental r WHERE r.endDate = :endDate"),
        @NamedQuery(name = "Rental.findByPrice", query = "SELECT r FROM Rental r WHERE r.price = :price"),
        @NamedQuery(name = "Rental.findByBusinessKey", query = "SELECT r FROM Rental r WHERE r.businessKey = :businessKey"),
        @NamedQuery(name = "Rental.findBetweenDatesWithYacht", query = "SELECT COUNT(r) FROM Rental r WHERE r.yacht.name = :name " +
                "and r.beginDate<= :endDate " +
                "and r.endDate>= :beginDate " +
                "and r.rentalStatus.name != :rentalStatusName"),
        @NamedQuery(name = "Rental.findByYachtName", query = "SELECT r FROM Rental r WHERE lower(r.yacht.name) like :name")})
public class Rental implements Serializable {

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
    @Column(name = "begin_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date beginDate;
    @Column(name = "end_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date endDate;
    @Column(name = "price", nullable = false, updatable = false)
    @NotNull
    @Digits(integer = 18,fraction = 2)
    private BigDecimal price;
    @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "rental")
    private Opinion opinion;
    @JoinColumn(name = "rental_status_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    @NotNull
    private RentalStatus rentalStatus;
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false, nullable = false)
    @ManyToOne(optional = false)
    @NotNull
    private User user;
    @JoinColumn(name = "yacht_id", referencedColumnName = "id", updatable = false, nullable = false)
    @ManyToOne(optional = false)
    @NotNull
    private Yacht yacht;

    public Rental() {
    }

    public Rental(Date beginDate, Date endDate, BigDecimal price, User user, Yacht yacht) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.price = price;
        this.user = user;
        this.yacht = yacht;

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

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Opinion getOpinion() {
        return opinion;
    }

    public void setOpinion(Opinion opinion) {
        this.opinion = opinion;
    }

    public RentalStatus getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(RentalStatus rentalStatusId) {
        this.rentalStatus = rentalStatusId;
    }

    public User getUser() {
        return user;
    }

    public Yacht getYacht() {
        return yacht;
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
        if (!(object instanceof Rental)) {
            return false;
        }
        Rental other = (Rental) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "pl.lodz.p.it.ssbd2020.ssbd02.entities.Rental[ id=" + id
                + ", version=" + version + " ]";
    }
}
