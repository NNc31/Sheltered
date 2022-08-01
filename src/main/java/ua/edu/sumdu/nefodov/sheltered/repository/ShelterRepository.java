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

    @Transactional
    @Modifying
    @Query("UPDATE Shelter s SET s.counter = s.counter + :cnt " +
            "WHERE s.coordinates.latitude = :lat AND s.coordinates.longitude = :lng")
    void refreshCount(@Param("lat") double lat, @Param("lng") double lng, @Param("cnt") int cnt);

    @Transactional
    @Modifying
    @Query("UPDATE Shelter s SET s.counter = 0 " +
            "WHERE s.coordinates.latitude = :lat AND s.coordinates.longitude = :lng")
    void resetCounter(@Param("lat") double lat, @Param("lng") double lng);

    @Transactional
    @Modifying
    @Query("UPDATE Shelter s SET s.counter = 0 ")
    void resetCounters();

    @Query("SELECT new ua.edu.sumdu.nefodov.sheltered.model.Coordinates(s.coordinates.latitude, s.coordinates.longitude) FROM Shelter s")
    List<Coordinates> findAllCoordinates();
}

