package ua.edu.sumdu.nefodov.sheltered.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.edu.sumdu.nefodov.sheltered.model.Coordinates;
import ua.edu.sumdu.nefodov.sheltered.model.Shelter;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Coordinates> {

    @Query("SELECT new ua.edu.sumdu.nefodov.sheltered.model.Coordinates(s.coordinates.latitude, s.coordinates.longitude) FROM Shelter s")
    List<Coordinates> findAllCoordinates();
}

