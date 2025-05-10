package com.nefodov.sheltered.shelterservice.repository;

import com.nefodov.sheltered.shelterservice.model.Coordinates;
import com.nefodov.sheltered.shelterservice.model.Shelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Coordinates> {

    @Query("SELECT new com.nefodov.sheltered.shelterservice.model.Coordinates(s.coordinates.latitude, s.coordinates.longitude) FROM Shelter s")
    List<Coordinates> findAllCoordinates();
}

