package ua.edu.sumdu.nefodov.sheltered.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ua.edu.sumdu.nefodov.sheltered.model.Shelter;
import ua.edu.sumdu.nefodov.sheltered.model.ShelterConditions;
import ua.edu.sumdu.nefodov.sheltered.model.ShelterStatus;
import ua.edu.sumdu.nefodov.sheltered.service.ShelterService;

import javax.validation.Valid;

@Controller
@RequestMapping("/shelter")
public class ShelteredController {

    private final ShelterService shelterService;

    @Autowired
    public ShelteredController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @GetMapping("/home")
    public String index(Model model) {
        Double lat = null, lng = null;
        model.addAttribute("lat", lat);
        model.addAttribute("lng", lng);

        model.addAttribute("shelters", shelterService.findAll());
        return "index";
    }

    @GetMapping("")
    public String showShelterSubmit(Model model,
                                    @RequestParam(value = "lat", required = false) Double lat,
                                    @RequestParam(value = "lng", required = false) Double lng) {
        Shelter shelter = shelterService.findByCoords(lat, lng);

        if (shelter == null) {
            model.addAttribute("error", "Неможливо відобразити сховище");
            return "/home";
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
        model.addAttribute("statuses", ShelterStatus.values());
        model.addAttribute("conditions", ShelterConditions.values());
        return "add";
    }

    @PostMapping("/add")
    public RedirectView addShelterSubmit(@Valid @ModelAttribute Shelter shelter, RedirectAttributes redirectAttributes) {
        shelterService.addShelter(shelter);
        redirectAttributes.addFlashAttribute("success", "Сховище успішно додано");
        return new RedirectView("/shelter/add-form", true);
    }

    @GetMapping("/edit-form")
    public String editShelter(Model model) {
        model.addAttribute("shelter", new Shelter());
        model.addAttribute("shelters", shelterService.findAll());
        model.addAttribute("statuses", ShelterStatus.values());
        model.addAttribute("conditions", ShelterConditions.values());
        return "edit";
    }

    @PostMapping("/edit")
    public RedirectView editShelterSubmit(@Valid @ModelAttribute Shelter shelter, RedirectAttributes redirectAttributes) {
        shelterService.updateShelter(shelter);
        redirectAttributes.addFlashAttribute("success", "Сховище успішно змінено");
        //redirectAttributes.addFlashAttribute("error", "Неможливо змінити сховище");

        return new RedirectView("/shelter/edit-form", true);
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
        return new RedirectView("/shelter/delete-form", true);
    }
}
