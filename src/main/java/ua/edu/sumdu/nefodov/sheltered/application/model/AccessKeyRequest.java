package ua.edu.sumdu.nefodov.sheltered.application.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class AccessKeyRequest {

    @Size(min = 1, message = "Ім'я не може бути менше 1 символу")
    @Size(max = 30, message = "Ім'я не може бути більше 30 символів")
    private String firstName;

    @Size(min = 1, message = "Прізвище не може бути менше 1 символу")
    @Size(max = 30, message = "Прізвище не може бути більше 30 символів")
    private String lastName;

    @Size(min = 1, message = "Назва організація не може бути менше 1 символу")
    @Size(max = 30, message = "Назва організація не може бути більше 40 символів")
    private String organisation;

    @Email(message = "Некоректна пошта")
    private String email;

    @Size(min = 1, message = "Номер телефону не може бути менше за 1")
    @Size(max = 14, message = "Номер телефону не може бути менше за 14")
    private String phone;

    public AccessKeyRequest() {

    }

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

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
