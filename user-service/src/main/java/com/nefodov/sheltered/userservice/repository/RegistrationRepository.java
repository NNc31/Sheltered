package com.nefodov.sheltered.userservice.repository;

import com.nefodov.sheltered.userservice.model.RegistrationApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationApplication, Long> {

    RegistrationApplication findByEmail(String email);
}
