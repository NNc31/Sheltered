package com.nefodov.sheltered.shelterservice.controller;

import com.nefodov.sheltered.shared.model.ShelterDTO;
import com.nefodov.sheltered.shelterservice.model.Coordinates;
import com.nefodov.sheltered.shelterservice.model.Shelter;
import com.nefodov.sheltered.shelterservice.service.ShelterService;
import com.nefodov.sheltered.shelterservice.service.mapper.ShelterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shelter-service")
public class ShelterRestController {

    private final ShelterService shelterService;
    private final ShelterMapper shelterMapper;

    @Autowired
    public ShelterRestController(ShelterService shelterService, ShelterMapper shelterMapper) {
        this.shelterService = shelterService;
        this.shelterMapper = shelterMapper;
    }

    @GetMapping
    public List<Shelter> getAll() {
        return shelterService.findAll();
    }

    @GetMapping("/find")
    public ResponseEntity<ShelterDTO> findByCoords(@RequestParam(name = "lat") Double lat, @RequestParam(name = "lng") Double lng) {
        Shelter shelter = shelterService.findByCoords(lat, lng);
        if (shelter == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(shelterMapper.toDTO(shelter));
        }
    }

    @PostMapping("/add")
    public ShelterDTO addShelter(@RequestBody ShelterDTO shelterDTO ) {
        return shelterMapper.toDTO(shelterService.addShelter(shelterMapper.toEntity(shelterDTO)));
    }

    @PutMapping("/update")
    public ShelterDTO updateShelter(@RequestBody ShelterDTO shelterDTO) {
        return shelterMapper.toDTO(shelterService.updateShelter(shelterMapper.toEntity(shelterDTO)));
    }

    @DeleteMapping("/delete")
    public void deleteShelter(@RequestParam("lat") Double lat, @RequestParam("lng") Double lng) {
        shelterService.deleteShelter(lat, lng);
    }

    @GetMapping(value = "/coords", produces = "application/json")
    public List<Coordinates> getSheltersCoordinates() {
        return shelterService.getAllCoords();
    }
}
