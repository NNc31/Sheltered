package ua.edu.sumdu.nefodov.sheltered.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.sumdu.nefodov.sheltered.repository.ShelterRepository;

@Component
public class ContextConfig {

    ShelterRepository shelterRepository;

    @Autowired
    public ContextConfig(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }
}
