package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_access_level", schema = "public", catalog = "ssbd02")
public class UserAccessLevelEntity {
    private long id;
    private long version;
    private Object businessKey;
    private UserEntity userByUserId;
    private AccessLevelEntity accessLevelByAccessLevelId;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "version", nullable = false)
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Basic
    @Column(name = "business_key", nullable = false)
    public Object getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(Object businessKey) {
        this.businessKey = businessKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccessLevelEntity that = (UserAccessLevelEntity) o;

        if (id != that.id) return false;
        if (version != that.version) return false;
        if (businessKey != null ? !businessKey.equals(that.businessKey) : that.businessKey != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (version ^ (version >>> 32));
        result = 31 * result + (businessKey != null ? businessKey.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "access_level_id", referencedColumnName = "id", nullable = false)
    public AccessLevelEntity getAccessLevelByAccessLevelId() {
        return accessLevelByAccessLevelId;
    }

    public void setAccessLevelByAccessLevelId(AccessLevelEntity accessLevelByAccessLevelId) {
        this.accessLevelByAccessLevelId = accessLevelByAccessLevelId;
    }
}
