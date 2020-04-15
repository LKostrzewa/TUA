package pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos;

public class ChangePasswordDTO implements Comparable<ChangePasswordDTO> {
    private long id;
    private String password;
    private String confirmPassword;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public int hashCode() {
        return password != null ? password.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "EditUserDTO{" +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int compareTo(ChangePasswordDTO changePasswordDTO) {
        return this.password.compareTo(changePasswordDTO.password);
    }
}
