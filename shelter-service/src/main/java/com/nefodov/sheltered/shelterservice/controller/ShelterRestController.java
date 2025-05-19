package com.nefodov.sheltered.shelterservice.controller;

import com.nefodov.sheltered.shared.model.ShelterDTO;
import com.nefodov.sheltered.shelterservice.exception.ShelterNotFoundException;
import com.nefodov.sheltered.shelterservice.model.Shelter;
import com.nefodov.sheltered.shelterservice.service.ShelterService;
import com.nefodov.sheltered.shelterservice.service.mapper.ShelterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<ShelterDTO>> getAll() {
        return ResponseEntity.ok(shelterService.findAll()
                .stream()
                .map(shelterMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/find")
    public ResponseEntity<ShelterDTO> findByCoords(@RequestParam(name = "lat") Double lat,
                                                   @RequestParam(name = "lng") Double lng) {
        try {
            Shelter shelter = shelterService.findByCoords(lat, lng);
            return ResponseEntity.ok(shelterMapper.toDTO(shelter));
        } catch (ShelterNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addShelter(@RequestBody ShelterDTO shelterDTO ) {
        if (shelterService.addShelter(shelterMapper.toEntity(shelterDTO)) != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateShelter(@RequestBody ShelterDTO shelterDTO) {
        if (shelterService.updateShelter(shelterMapper.toEntity(shelterDTO)) != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteShelter(@RequestParam("lat") Double lat, @RequestParam("lng") Double lng) {
        try {
            shelterService.deleteShelter(lat, lng);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /*
    @GetMapping(value = "/coords", produces = "application/json")
    public List<Coordinates> getSheltersCoordinates() {
        return shelterService.getAllCoords();
    }
     */
}
