package ua.edu.sumdu.nefodov.sheltered.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ua.edu.sumdu.nefodov.sheltered.model.Shelter;
import ua.edu.sumdu.nefodov.sheltered.service.ShelterService;

import java.util.List;

@Controller
public class ShelteredController {

    private ShelterService shelterService;

    @Autowired
    public ShelteredController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @GetMapping("/")
    public String index(Model model) {
        Double lat = null, lng = null;
        model.addAttribute("lat", lat);
        model.addAttribute("lng", lng);

        model.addAttribute("shelters", shelterService.findAll());
        return "index";
    }

    @GetMapping("/shelter")
    public String showShelterSubmit(Model model,
                                    @RequestParam(value = "lat", required = false) Double lat,
                                    @RequestParam(value = "lng", required = false) Double lng) {
        Shelter shelter = shelterService.findByCoords(lat, lng);

        if (shelter == null) {
            model.addAttribute("error", "Неможливо відобразити сховище");
            return "/";
        } else {
            model.addAttribute("shelter", shelter);
            model.addAttribute("statusLabel", shelter.getStatus().label);
            return "shelter";
        }
    }

    @GetMapping("/add-form")
    public String addShelter(Model model) {
        model.addAttribute("shelter", new Shelter());
        model.addAttribute("shelters", shelterService.findAll());
        return "add";
    }

    @PostMapping("/add")
    public RedirectView addShelterSubmit(@ModelAttribute Shelter shelter, RedirectAttributes redirectAttributes) {
        if (Shelter.isValidShelter(shelter)) {
            shelterService.addShelter(shelter);
            redirectAttributes.addFlashAttribute("success", "Сховище успішно додано");
        } else {
            redirectAttributes.addFlashAttribute("error", "Неможливо додати сховище");
        }
        return new RedirectView("/add-form", true);
    }

    @GetMapping("/edit-form")
    public String editShelter(Model model) {
        model.addAttribute("shelter", new Shelter());
        model.addAttribute("shelters", shelterService.findAll());
        return "edit";
    }

    @PostMapping("/edit")
    public RedirectView editShelterSubmit(@ModelAttribute Shelter shelter, RedirectAttributes redirectAttributes) {
        if (Shelter.isValidShelter(shelter)) {
            shelterService.updateShelter(shelter);
            redirectAttributes.addFlashAttribute("success", "Сховище успішно змінено");
        } else {
            redirectAttributes.addFlashAttribute("error", "Неможливо змінити сховище");
        }
        return new RedirectView("/edit-form", true);
    }

    @GetMapping("/delete-form")
    public String deleteShelter(Model model) {
        Double lat = null, lng = null;
        model.addAttribute("lat", lat);
        model.addAttribute("lng", lng);

        model.addAttribute("shelters", shelterService.findAll());
        return "delete";
    }

    @PostMapping("/delete")
    public RedirectView deleteShelterSubmit(@RequestParam(value = "lat", required = false) Double lat,
                                            @RequestParam(value = "lng", required = false) Double lng,
                                            RedirectAttributes redirectAttributes) {
        if (lat == null || lng == null) {
            redirectAttributes.addFlashAttribute("error", "Неможливо видалити сховище");

        } else {
            shelterService.deleteShelter(lat, lng);
            redirectAttributes.addFlashAttribute("success", "Сховище успішно видалено");
        }
        return new RedirectView("/delete-form", true);
    }
}
