package ua.edu.sumdu.nefodov.sheltered.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.edu.sumdu.nefodov.sheltered.application.model.Coordinates;
import ua.edu.sumdu.nefodov.sheltered.application.model.Shelter;

import java.util.List;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Coordinates> {

    @Query("SELECT new ua.edu.sumdu.nefodov.sheltered.application.model.Coordinates(s.coordinates.latitude, s.coordinates.longitude) FROM Shelter s")
    List<Coordinates> findAllCoordinates();
}

