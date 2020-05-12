package pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos;


import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class UserAccessLevelDto {

    private MutablePair<Boolean,Boolean> admin = new MutablePair<Boolean,Boolean>(false,false);
    private MutablePair<Boolean,Boolean> manager = new MutablePair<Boolean,Boolean>(false,false);
    private MutablePair<Boolean,Boolean> client = new MutablePair<Boolean,Boolean>(false,false);
    private UserDetailsDto userDetailsDto;

    public MutablePair<Boolean, Boolean> getAdmin() {
        return admin;
    }

    public void setAdmin(MutablePair<Boolean, Boolean> admin) {
        this.admin = admin;
    }

    public MutablePair<Boolean, Boolean> getManager() {
        return manager;
    }

    public void setManager(MutablePair<Boolean, Boolean> manager) {
        this.manager = manager;
    }

    public MutablePair<Boolean, Boolean> getClient() {
        return client;
    }

    public void setClient(MutablePair<Boolean, Boolean> client) {
        this.client = client;
    }

    public UserDetailsDto getUserDetailsDto() {
        return userDetailsDto;
    }

    public void setUserDetailsDto(UserDetailsDto userDetailsDto) {
        this.userDetailsDto = userDetailsDto;
    }
}
