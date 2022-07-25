package ua.edu.sumdu.nefodov.sheltered.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.edu.sumdu.nefodov.sheltered.model.Shelter;
import ua.edu.sumdu.nefodov.sheltered.model.ShelterStatus;
import ua.edu.sumdu.nefodov.sheltered.repository.ShelterRepository;
import ua.edu.sumdu.nefodov.sheltered.service.ShelterService;

import java.util.Iterator;
import java.util.List;

@Controller
public class ShelteredController {

    //private ShelterService shelterService;

    private ShelterRepository shelterRepo;

    /*
    @Autowired
    public ShelteredController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

     */

    @Autowired
    public ShelteredController(ShelterRepository shelterRepo) {
        System.out.println("autowired repo");
        this.shelterRepo = shelterRepo;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/add-form")
    public String addShelter(Model model) {
        model.addAttribute("shelter", new Shelter());
        model.addAttribute("shelters", shelterRepo.findAll());
        return "addShelter";
    }

    @PostMapping("/add")
    public String addShelterSubmit(@ModelAttribute Shelter shelter, Model model) {
        model.addAttribute("shelter", shelter);
        /*
        Model model, @RequestParam String coords,
        RequestParam double area,
                             @RequestParam int capacity,
                             @RequestParam boolean food,
                             @RequestParam boolean water,
                             @RequestParam boolean electricity,
                             @RequestParam String additional,
                             @RequestParam String status
        Shelter shelter = new Shelter();
        shelterRepo.save(shelter);
         */

        //System.out.println("POST ADD");
        //System.out.println(shelter.getArea());
        //System.out.println(shelter.getCapacity());
        //System.out.println(shelter.isFood());
        //System.out.println(shelter.isWater());
        //System.out.println(shelter.isElectricity());


        //System.out.println(shelter.getAdditional()); //?
        //System.out.println(shelter.getStatus()); //?
        return "index";
    }

    @GetMapping("/edit")
    public String editShelter() {
        return "editShelter";
    }

    @GetMapping("/delete")
    public String deleteShelter() {
        return "deleteShelter";
    }
}
