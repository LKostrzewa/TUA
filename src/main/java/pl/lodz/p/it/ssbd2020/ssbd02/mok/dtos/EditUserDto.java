package pl.lodz.p.it.ssbd2020.ssbd02.mok.dtos;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EditUserDto {
    @Size(min = 2, message = "{validation.first_name}")
    private String firstName;
    @Size(min = 2, message = "{validation.last_name}")
    private String lastName;
    @Pattern(regexp = "\\d{9}", message = "{validation.number}")
    private String phoneNumber;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
