package com.nefodov.sheltered.webservice.controller;

import com.nefodov.sheltered.shared.model.ShelterCondition;
import com.nefodov.sheltered.shared.model.ShelterDTO;
import com.nefodov.sheltered.shared.model.ShelterStatus;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/web-service/shelter")
public class ShelterWebController {

    private final RestTemplate restTemplate;

    @Autowired
    public ShelterWebController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @GetMapping("/home")
    public String index(Model model) {
        Double lat = null, lng = null;
        ShelterDTO[] shelters = restTemplate.getForObject("http://api-gateway:8080/shelter-service", ShelterDTO[].class);
        model.addAttribute("lat", lat);
        model.addAttribute("lng", lng);
        model.addAttribute("shelters", shelters);
        model.addAttribute("statuses", ShelterStatus.values());
        model.addAttribute("conditions", ShelterCondition.values());
        return "index";
    }

    @GetMapping("")
    public String showShelterSubmit(Model model,
                                    @RequestParam(value = "lat", required = false) Double lat,
                                    @RequestParam(value = "lng", required = false) Double lng) {
        String url = "http://api-gateway:8080/shelter-service/find?lat={lat}&lng={lng}";
        Map<String, Object> params = new HashMap<>();
        params.put("lat", lat);
        params.put("lng", lng);

        ShelterDTO shelter = restTemplate.getForObject(url, ShelterDTO.class, params);

        if (shelter == null) {
            model.addAttribute("error", "Неможливо відобразити сховище");
            return "redirect:/shelter/home";
        } else {
            model.addAttribute("shelter", shelter);
            model.addAttribute("statusLabel", shelter.getStatus().label);
            return "shelter";
        }
    }

    @GetMapping("/add-form")
    public String addShelter(Model model) {
        ShelterDTO[] shelters = restTemplate.getForObject("http://api-gateway:8080/shelter-service", ShelterDTO[].class);
        model.addAttribute("shelter", new ShelterDTO());
        model.addAttribute("shelters", shelters);
        model.addAttribute("statuses", ShelterStatus.values());
        model.addAttribute("conditions", ShelterCondition.values());

        return "add";
    }


    @PostMapping("/add")
    public RedirectView addShelterSubmit(@Valid @ModelAttribute ShelterDTO shelter, HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("token");
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<ShelterDTO> entity = new HttpEntity<>(shelter, headers);

        ResponseEntity<ShelterDTO> response = restTemplate.exchange(
                "http://api-gateway:8080/shelter-service/add",
                HttpMethod.POST,
                entity,
                ShelterDTO.class
        );

        redirectAttributes.addFlashAttribute("success", "Сховище успішно додано");
        return new RedirectView("/web-service/shelter/add-form", true);
    }

    @GetMapping("/edit-form")
    public String editShelter(Model model) {
        model.addAttribute("shelter", new ShelterDTO());
        ShelterDTO[] shelters = restTemplate.getForObject("http://api-gateway:8080/shelter-service", ShelterDTO[].class);
        model.addAttribute("shelters", shelters);
        model.addAttribute("statuses", ShelterStatus.values());
        model.addAttribute("conditions", ShelterCondition.values());
        return "edit";
    }

    @PostMapping("/edit")
    public RedirectView editShelterSubmit(@Valid @ModelAttribute ShelterDTO shelter, HttpSession session, RedirectAttributes redirectAttributes) {
        String token = (String) session.getAttribute("token");
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        HttpEntity<ShelterDTO> entity = new HttpEntity<>(shelter, headers);

        ResponseEntity<ShelterDTO> response = restTemplate.exchange(
                "http://api-gateway:8080/shelter-service/update",
                HttpMethod.PUT,
                entity,
                ShelterDTO.class
        );

        redirectAttributes.addFlashAttribute("success", "Сховище успішно змінено");
        //redirectAttributes.addFlashAttribute("error", "Неможливо змінити сховище");

        return new RedirectView("/web-service/shelter/edit-form", true);
    }

    @GetMapping("/delete-form")
    public String deleteShelter(Model model) {
        Double lat = null, lng = null;
        model.addAttribute("lat", lat);
        model.addAttribute("lng", lng);
        ShelterDTO[] shelters = restTemplate.getForObject("http://api-gateway:8080/shelter-service", ShelterDTO[].class);
        model.addAttribute("shelters", shelters);
        return "delete";
    }

    @PostMapping("/delete")
    public RedirectView deleteShelterSubmit(@RequestParam(value = "lat", required = false) Double lat,
                                            @RequestParam(value = "lng", required = false) Double lng,
                                            HttpSession session,
                                            RedirectAttributes redirectAttributes) {

        if (lat == null || lng == null) {
            redirectAttributes.addFlashAttribute("error", "Неможливо видалити сховище");
        } else {
            String token = (String) session.getAttribute("token");
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);

            ResponseEntity<Void> response = restTemplate.exchange(
                    "http://api-gateway:8080/shelter-service/delete?lat=" + lat + "&lng=" + lng,
                    HttpMethod.DELETE,
                    new HttpEntity<>(headers),
                    Void.class
            );

            redirectAttributes.addFlashAttribute("success", "Сховище успішно видалено");
        }
        return new RedirectView("/web-service/shelter/delete-form", true);
    }
}
