package pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos;

import javax.validation.constraints.Pattern;

/**
 * DTO do resetowania hasła
 */
public class ResetPasswordDto {

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "{validation.password}")
    private String password;
    private String resetPasswordCode;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResetPasswordCode() {
        return resetPasswordCode;
    }

    public void setResetPasswordCode(String resetPasswordCode) {
        this.resetPasswordCode = resetPasswordCode;
    }
}
