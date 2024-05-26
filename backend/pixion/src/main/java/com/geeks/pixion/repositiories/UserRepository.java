package com.geeks.pixion.repositiories;

import com.geeks.pixion.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByFirstNameContaining(String firstName);
}
