package com.nefodov.sheltered.userservice.service;

import com.nefodov.sheltered.shared.model.RegistrationRequestDTO;
import com.nefodov.sheltered.userservice.model.RegistrationApplication;
import com.nefodov.sheltered.userservice.model.Role;
import com.nefodov.sheltered.userservice.model.User;
import com.nefodov.sheltered.userservice.repository.RegistrationRepository;
import com.nefodov.sheltered.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final RegistrationRepository registrationRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final String ADMINISTRATOR_EMAIL;

    @Autowired
    public UserService(RegistrationRepository registrationRepository,
                       UserRepository userRepository,
                       JavaMailSender mailSender, @Value("${spring.mail.username}") String administratorEmail) {
        this.registrationRepository = registrationRepository;
        this.userRepository = userRepository;
        this.mailSender = mailSender;
        ADMINISTRATOR_EMAIL = administratorEmail;
    }

    public User getUserByEmail(String email) {
        List<User> users = userRepository.findUserByEmail(email);
        if (!users.isEmpty()) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public boolean saveRegistrationApplication(RegistrationApplication registrationApplication) {
        String key = UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
        registrationApplication.setRegistrationCode(key);
        registrationRepository.save(registrationApplication);
        mailSender.send(prepareMailMessage(registrationApplication));
        return true;
    }

    public boolean registerUser(RegistrationRequestDTO request) {
        RegistrationApplication registrationApplication = registrationRepository.findByEmail(request.getEmail());
        if (registrationApplication != null && request.getAccessKey().equals(registrationApplication.getRegistrationCode())) {
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setRole(Role.USER);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    private SimpleMailMessage prepareMailMessage(RegistrationApplication registrationApplication) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(ADMINISTRATOR_EMAIL);
        mail.setSubject("Sheltered: New Registration Application from " + registrationApplication.getEmail());
        mail.setText("Нова заявка на реєстрацію"
                + "\nПовне ім'я: " + registrationApplication.getFullName()
                + "\nПошта: " + registrationApplication.getEmail()
                + "\nОрганізація: " + registrationApplication.getOrganisation()
                + "\nРеєстраційний код: " + registrationApplication.getRegistrationCode());
        return mail;
    }
}
