package ua.edu.sumdu.nefodov.sheltered.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.sumdu.nefodov.sheltered.model.Coordinates;
import ua.edu.sumdu.nefodov.sheltered.model.Shelter;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Coordinates> {

}

