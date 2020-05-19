package pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos;

import javax.validation.constraints.NotBlank;

public class ChangeOwnPasswordDto {
    @NotBlank(message = "{oldPassword.message}")
    private String oldPassword;
    @NotBlank(message = "{newPassword.message}")
    private String password;
    @NotBlank(message = "{confirmPassword.message}")
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
}
