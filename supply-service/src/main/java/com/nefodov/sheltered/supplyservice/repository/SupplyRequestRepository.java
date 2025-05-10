package com.nefodov.sheltered.supplyservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nefodov.sheltered.supplyservice.model.SupplyRequest;

@Repository
public interface SupplyRequestRepository extends JpaRepository<SupplyRequest, Long> {

}
