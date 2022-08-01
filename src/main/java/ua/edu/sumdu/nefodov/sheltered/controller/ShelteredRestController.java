package ua.edu.sumdu.nefodov.sheltered.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.edu.sumdu.nefodov.sheltered.model.Coordinates;
import ua.edu.sumdu.nefodov.sheltered.model.Shelter;
import ua.edu.sumdu.nefodov.sheltered.service.ShelterService;

import java.util.List;


@RestController
@RequestMapping("/api")
public class ShelteredRestController {

    private final ShelterService shelterService;

    @Autowired
    public ShelteredRestController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @GetMapping(value = "/shelter", produces = "application/json")
    public Shelter getShelter(@RequestParam(value = "lat") double lat, @RequestParam(value = "lng") double lng) {
        return shelterService.findByCoords(lat, lng);
    }

    @GetMapping(value = "/shelters", produces = "application/json")
    public List<Shelter> getShelters() {
        return shelterService.findAll();
    }

    @GetMapping(value = "/coords", produces = "application/json")
    public List<Coordinates> getSheltersCoordinates() {
        return shelterService.getAllCoords();
    }

}
