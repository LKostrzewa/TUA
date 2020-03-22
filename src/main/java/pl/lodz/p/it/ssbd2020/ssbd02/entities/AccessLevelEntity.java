package pl.lodz.p.it.ssbd2020.ssbd02.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "access_level", schema = "public", catalog = "ssbd02")
public class AccessLevelEntity {
    private long id;
    private long version;
    private Object businessKey;
    private String name;
    private Collection<UserAccessLevelEntity> userAccessLevelsById;

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

    @Basic
    @Column(name = "name", nullable = false, length = 32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccessLevelEntity that = (AccessLevelEntity) o;

        if (id != that.id) return false;
        if (version != that.version) return false;
        if (businessKey != null ? !businessKey.equals(that.businessKey) : that.businessKey != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (version ^ (version >>> 32));
        result = 31 * result + (businessKey != null ? businessKey.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "accessLevelByAccessLevelId")
    public Collection<UserAccessLevelEntity> getUserAccessLevelsById() {
        return userAccessLevelsById;
    }

    public void setUserAccessLevelsById(Collection<UserAccessLevelEntity> userAccessLevelsById) {
        this.userAccessLevelsById = userAccessLevelsById;
    }
}
