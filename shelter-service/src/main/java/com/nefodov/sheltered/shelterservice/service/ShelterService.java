package com.nefodov.sheltered.shelterservice.service;

import com.nefodov.sheltered.shelterservice.exception.ShelterNotFoundException;
import com.nefodov.sheltered.shelterservice.model.Coordinates;
import com.nefodov.sheltered.shelterservice.model.Shelter;
import com.nefodov.sheltered.shelterservice.repository.ShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelterService {

    private final ShelterRepository shelterRepo;

    @Autowired
    public ShelterService(ShelterRepository shelterRepo) {
        this.shelterRepo = shelterRepo;
    }

    public List<Shelter> findAll() {
        return shelterRepo.findAll();
    }

    public Shelter findByCoords(double lat, double lng) {
        Coordinates coords = new Coordinates(lat, lng);
        if (!shelterRepo.existsById(coords)) {
            throw new ShelterNotFoundException(coords);
        }
        return shelterRepo.findById(new Coordinates(lat, lng)).orElse(null);
    }

    public Shelter addShelter(Shelter newShelter) {
        return shelterRepo.save(newShelter);
    }

    public Shelter updateShelter(Shelter updatedShelter) {
        Coordinates coords = updatedShelter.getCoordinates();
        if (!shelterRepo.existsById(coords)) {
            throw new ShelterNotFoundException(coords);
        }
        return shelterRepo.save(updatedShelter);
    }

    public void deleteShelter(double lat, double lng) {
        Coordinates coords = new Coordinates(lat, lng);
        if (!shelterRepo.existsById(coords)) {
            throw new ShelterNotFoundException(coords);
        }
        shelterRepo.deleteById(coords);
    }

    public List<Coordinates> getAllCoords() {
        return shelterRepo.findAllCoordinates();
    }
}
