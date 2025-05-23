package com.nefodov.sheltered.userservice.repository;

import com.nefodov.sheltered.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUserByEmail(String email);
}
