package ua.edu.sumdu.nefodov.sheltered.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.edu.sumdu.nefodov.sheltered.application.model.AccessKey;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccessKeyRepository extends JpaRepository<AccessKey, String> {

    List<AccessKey> findAllByKey(String key);
    Optional<AccessKey> findByEmail(String email);

}
