package ua.edu.sumdu.nefodov.sheltered.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.edu.sumdu.nefodov.sheltered.model.Shelter;
import ua.edu.sumdu.nefodov.sheltered.repository.ShelterRepository;
import ua.edu.sumdu.nefodov.sheltered.service.CounterService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Component
public class ContextConfig {

    ShelterRepository shelterRepository;
    CounterService counterService;

    @Autowired
    public ContextConfig(ShelterRepository shelterRepository, CounterService counterService) {
        this.shelterRepository = shelterRepository;
        this.counterService = counterService;
    }

    @PostConstruct
    public void postConstruct() {
        shelterRepository.resetCounters();
        counterService.enableCounter();
    }

    @PreDestroy
    public void destroy() {
        counterService.disableCounter();
    }
}
