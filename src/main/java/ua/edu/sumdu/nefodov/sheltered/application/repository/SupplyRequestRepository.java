package ua.edu.sumdu.nefodov.sheltered.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.sumdu.nefodov.sheltered.application.model.SupplyRequest;

@Repository
public interface SupplyRequestRepository extends JpaRepository<SupplyRequest, Long> {


}
