package ua.edu.sumdu.nefodov.sheltered.application.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class RegistrationRequest {

    @Email(message = "Некоректна пошта")
    private String email;

    @Size(min = 8, message = "Мінімальна довжина паролю 8 символів")
    @Size(max = 16, message = "Максимальна довжина паролю 16 символів")
    private String password;

    @Size(min = 36, max = 36, message = "Некоректний ключ")
    private String accessKey;

    public RegistrationRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
}
