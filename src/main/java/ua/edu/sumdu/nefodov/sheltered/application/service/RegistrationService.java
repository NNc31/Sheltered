package ua.edu.sumdu.nefodov.sheltered.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.nefodov.sheltered.application.model.*;
import ua.edu.sumdu.nefodov.sheltered.application.repository.AccessKeyRepository;
import ua.edu.sumdu.nefodov.sheltered.application.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RegistrationService {

    private final UserRepository userRepository;
    private final AccessKeyRepository accessKeyRepository;
    private final EmailSenderService emailSender;

    private  final BCryptPasswordEncoder bCryptPasswordEncoder;


    private final String KEY_REQUEST_MAIL_TO;
    private final String KEY_REQUEST_MAIL_SUBJECT = "[Sheltered] Нова заявка на ключ від ";
    private final UserRole DEFAULT_ROLE = UserRole.ROLE_USER;

    @Autowired
    public RegistrationService(UserRepository userRepository,
                               AccessKeyRepository accessKeyRepository,
                               EmailSenderService emailSender,
                               BCryptPasswordEncoder bCryptPasswordEncoder,
                               @Value("${administrator.mail}") String keyRequestMailTo) {
        this.userRepository = userRepository;
        this.accessKeyRepository = accessKeyRepository;
        this.emailSender = emailSender;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        KEY_REQUEST_MAIL_TO = keyRequestMailTo;
    }

    public void sendAccessKeyRequest(AccessKeyRequest request) {
        String key;
        do {
            key = UUID.randomUUID().toString();
        } while (!accessKeyRepository.findAllByKey(key).isEmpty());

        if (accessKeyRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("Заявку із такою поштою вже надіслано");
        }
        accessKeyRepository.save(new AccessKey(request.getEmail(), key));

        emailSender.send(KEY_REQUEST_MAIL_TO,
                KEY_REQUEST_MAIL_SUBJECT + request.getOrganisation(),
                prepareKeyRequestText(request, key));
    }

    public boolean registerNewUser(RegistrationRequest request) {

        List<AccessKey> keys = accessKeyRepository.findAllByKey(request.getAccessKey());
        String email = request.getEmail();

        if (userRepository.findByUsername(email).isEmpty() &&
                keys.contains(new AccessKey(email, request.getAccessKey()))) {
            String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());
            ShelteredUser user = new ShelteredUser(email, encodedPassword, DEFAULT_ROLE, true, false);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }


    }

    private String prepareKeyRequestText(AccessKeyRequest request, String key) {
        return "Новий запит на отримання ключа доступу до Sheltered!\n\n" +
                "Прізвище та ім'я: " +
                request.getLastName() + " " +
                request.getFirstName() + "\n" +
                "Організація: " + request.getOrganisation() + "\n" +
                "Пошта: " + request.getEmail() + "\n" +
                "Телефон: " + request.getPhone() + "\n" +
                "Згенерований ключ: " + key;
    }
}
