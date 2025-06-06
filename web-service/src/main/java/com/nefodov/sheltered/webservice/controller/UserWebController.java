package com.nefodov.sheltered.webservice.controller;

import com.nefodov.sheltered.shared.model.LoginRequestDTO;
import com.nefodov.sheltered.shared.model.RegistrationApplicationDTO;
import com.nefodov.sheltered.shared.model.RegistrationRequestDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/web-service/user")
public class UserWebController {

    private final RestTemplate restTemplate;
    private final String API_GATEWAY_BASE_URL = "http://localhost:8080";

    @Autowired
    public UserWebController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/registration-application")
    public String registrationApplication(Model model) {
        model.addAttribute("application", new RegistrationApplicationDTO());
        return "registration-application";
    }

    @PostMapping("/registration-application")
    public String registrationApplicationSubmit(@Valid @ModelAttribute RegistrationApplicationDTO application,
                                            RedirectAttributes redirectAttributes) {
        HttpEntity<RegistrationApplicationDTO> entity = new HttpEntity<>(application);
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://api-gateway:8080/user-service/registration-application",
                HttpMethod.POST,
                entity,
                Void.class
        );
        redirectAttributes.addFlashAttribute("success", "Заявку на реєстрацію відправлено");
        return "redirect:" + API_GATEWAY_BASE_URL + "/web-service/user/registration-application";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("request", new RegistrationRequestDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationSubmit(@Valid @ModelAttribute RegistrationRequestDTO request, Model model) {
        HttpEntity<RegistrationRequestDTO> entity = new HttpEntity<>(request);
        ResponseEntity<Void> response = restTemplate.exchange(
                "http://api-gateway:8080/user-service/registration",
                HttpMethod.POST,
                entity,
                Void.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return login(model);
        } else {
            return registration(model);
        }
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("request", new LoginRequestDTO());
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute LoginRequestDTO request, Model model, HttpSession session) {
        HttpEntity<LoginRequestDTO> entity = new HttpEntity<>(request);
        ResponseEntity<String> response = restTemplate.exchange(
                "http://api-gateway:8080/user-service/login",
                HttpMethod.POST,
                entity,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            session.setAttribute("JWT_TOKEN", response.getBody());
            return "redirect:" + API_GATEWAY_BASE_URL + "/web-service/shelter/home";
        } else {
            return login(model);
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("JWT_TOKEN");
        return "redirect:" + API_GATEWAY_BASE_URL + "/web-service/shelter/home";
    }
}
