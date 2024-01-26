package ua.edu.sumdu.nefodov.sheltered.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ua.edu.sumdu.nefodov.sheltered.application.model.Coordinates;
import ua.edu.sumdu.nefodov.sheltered.application.model.Shelter;
import ua.edu.sumdu.nefodov.sheltered.application.model.SupplyRequest;
import ua.edu.sumdu.nefodov.sheltered.application.service.ShelterService;
import ua.edu.sumdu.nefodov.sheltered.application.service.SupplyRequestService;

import javax.validation.Valid;

@Controller
@RequestMapping("/volunteering")
public class VolunteerController {

    private final SupplyRequestService srService;
    private final ShelterService shelterService;

    @Autowired
    public VolunteerController(SupplyRequestService srService, ShelterService shelterService) {
        this.srService = srService;
        this.shelterService = shelterService;
    }

    @GetMapping("/requests")
    public String supplyRequest(Model model) {
        model.addAttribute("supplyRequests", srService.findAll());
        return "supply-requests";
    }

    @GetMapping("/add-request")
    public String addSupplyRequest(Model model) {
        model.addAttribute("shelters", shelterService.findAll());
        model.addAttribute("supplyRequest", new SupplyRequest());
        return "add-supply-request";
    }

    @PostMapping("/add-request")
    public RedirectView addSupplyRequest(@Valid @ModelAttribute SupplyRequest supplyRequest, RedirectAttributes redirectAttributes) {
        Coordinates coords = supplyRequest.getShelter().getCoordinates();
        Shelter selectedShelter = shelterService.findByCoords(coords.getLatitude(), coords.getLongitude());
        supplyRequest.setShelter(selectedShelter);
        srService.addSupplyRequest(supplyRequest);
        redirectAttributes.addFlashAttribute("success", "Заявку успішно додано");
        return new RedirectView("/volunteering/requests", true);
    }
}
