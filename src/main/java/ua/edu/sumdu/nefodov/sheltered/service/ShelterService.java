package ua.edu.sumdu.nefodov.sheltered.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.nefodov.sheltered.model.Coordinates;
import ua.edu.sumdu.nefodov.sheltered.model.Shelter;
import ua.edu.sumdu.nefodov.sheltered.repository.ShelterRepository;

import java.util.List;
import java.util.Optional;

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

    public void addShelter(Shelter newShelter) {
        shelterRepo.save(newShelter);
    }

    public void updateShelter(Shelter updatedShelter) {
        Shelter shelterToUpdate = shelterRepo.getReferenceById(new Coordinates(updatedShelter.getLatitude(), updatedShelter.getLongitude()));

        shelterToUpdate.setStatus(updatedShelter.getStatus());
        shelterToUpdate.setConditions(updatedShelter.getConditions());
        shelterToUpdate.setCapacity(updatedShelter.getCapacity());
        shelterToUpdate.setArea(updatedShelter.getArea());
        shelterToUpdate.setAdditional(updatedShelter.getAdditional());

        shelterRepo.save(shelterToUpdate);
    }

    public void deleteShelter(double lat, double lng) {
        Coordinates coords = new Coordinates(lat, lng);
        shelterRepo.deleteById(coords);
    }
}
