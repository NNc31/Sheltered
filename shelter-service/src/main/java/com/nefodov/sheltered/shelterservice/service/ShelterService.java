package com.nefodov.sheltered.shelterservice.service;

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
        return shelterRepo.findById(new Coordinates(lat, lng)).orElse(null);
    }

    public Shelter addShelter(Shelter newShelter) {
        return shelterRepo.save(newShelter);
    }

    public Shelter updateShelter(Shelter updatedShelter) {
        Coordinates coords = new Coordinates(updatedShelter.getCoordinates().getLatitude(), updatedShelter.getCoordinates().getLongitude());
        Shelter shelterToUpdate = shelterRepo.findById(coords).orElse(null);

        if (shelterToUpdate != null) {
            shelterToUpdate.setStatus(updatedShelter.getStatus());
            shelterToUpdate.setConditions(updatedShelter.getConditions());
            shelterToUpdate.setCapacity(updatedShelter.getCapacity());
            shelterToUpdate.setArea(updatedShelter.getArea());
            shelterToUpdate.setAdditional(updatedShelter.getAdditional());

            return shelterRepo.save(shelterToUpdate);
        }
        return null;
    }

    public void deleteShelter(double lat, double lng) {
        Coordinates coords = new Coordinates(lat, lng);
        shelterRepo.deleteById(coords);
    }

    public List<Coordinates> getAllCoords() {
        return shelterRepo.findAllCoordinates();
    }
}
