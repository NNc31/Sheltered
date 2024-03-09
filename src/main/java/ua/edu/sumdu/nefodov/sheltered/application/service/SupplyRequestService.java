package ua.edu.sumdu.nefodov.sheltered.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.sumdu.nefodov.sheltered.application.model.SupplyRequest;
import ua.edu.sumdu.nefodov.sheltered.application.repository.SupplyRequestRepository;

import java.util.Date;
import java.util.List;

@Service
public class SupplyRequestService {

    private final SupplyRequestRepository srRepo;

    @Autowired
    public SupplyRequestService(SupplyRequestRepository srRepo) {
        this.srRepo = srRepo;
    }

    public List<SupplyRequest> findAll() {
        return srRepo.findAll();
    }

    public SupplyRequest findById(long id) {
        return srRepo.findById(id).orElse(null);
    }

    public void addSupplyRequest(SupplyRequest supplyRequest) {
        supplyRequest.setSubmitDate(new Date());
        srRepo.save(supplyRequest);
    }
}
