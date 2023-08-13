package ua.edu.sumdu.nefodov.sheltered.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ua.edu.sumdu.nefodov.sheltered.application.model.AccessKeyRequest;
import ua.edu.sumdu.nefodov.sheltered.application.model.RegistrationRequest;
import ua.edu.sumdu.nefodov.sheltered.application.service.RegistrationService;
import ua.edu.sumdu.nefodov.sheltered.application.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RegistrationService registrationService;

    @Autowired
    public UserController (UserService userService, RegistrationService registrationService) {
        this.userService = userService;
        this.registrationService = registrationService;
    }

    @GetMapping("/receive-key")
    public String receiveKey(Model model) {
        model.addAttribute("request", new AccessKeyRequest());
        return "access-key";
    }

    @PostMapping("/receive-key")
    public RedirectView receiveKeySubmit(@Valid @ModelAttribute AccessKeyRequest request, RedirectAttributes redirectAttributes) {
        registrationService.sendAccessKeyRequest(request);
        redirectAttributes.addFlashAttribute("success", "Заявку успішно надіслано. Очікуйте відповіді");
        return new RedirectView("/user/receive-key", true);
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("request", new RegistrationRequest());
        return "registration";
    }

    @PostMapping("/registration")
    public RedirectView registrationSubmit(@Valid @ModelAttribute RegistrationRequest request, RedirectAttributes redirectAttributes) {
        String redirectUrl;
        if (registrationService.registerNewUser(request)) {
            redirectUrl = "/shelter/home";
            //автоматичний логін?
        } else {
            redirectAttributes.addFlashAttribute("error", "Неможливо зареєструвати користувача");
            redirectUrl = "/user/registration";
        }
        return new RedirectView(redirectUrl, true);
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String customLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /*
    @PostMapping("/login")
    public RedirectView submitLogin() {
        return new RedirectView();
    }

     */
}
