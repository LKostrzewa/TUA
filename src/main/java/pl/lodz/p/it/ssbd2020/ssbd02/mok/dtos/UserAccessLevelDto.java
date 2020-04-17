package pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos;

public class UserAccessLevelDto {
    private Long id;
    private Boolean admin = false;
    private Boolean manager = false;
    private Boolean client = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getManager() {
        return manager;
    }

    public void setManager(Boolean manager) {
        this.manager = manager;
    }

    public Boolean getClient() {
        return client;
    }

    public void setClient(Boolean client) {
        this.client = client;
    }
}
