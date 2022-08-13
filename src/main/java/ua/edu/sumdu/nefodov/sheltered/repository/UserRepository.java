package ua.edu.sumdu.nefodov.sheltered.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.sumdu.nefodov.sheltered.model.ShelteredUser;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<ShelteredUser, String> {

    Optional<ShelteredUser> findByUsername(String email);
}
