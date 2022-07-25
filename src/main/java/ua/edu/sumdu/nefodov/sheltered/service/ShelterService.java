package ua.edu.sumdu.nefodov.sheltered.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.nefodov.sheltered.model.Shelter;
import ua.edu.sumdu.nefodov.sheltered.repository.ShelterRepository;

//@Service
public class ShelterService {

    private final ShelterRepository repository;

    //@Autowired
    public ShelterService(ShelterRepository repository) {
        this.repository = repository;
    }

    public Iterable<Shelter> findAll() {
        return repository.findAll();
    }
}
