package com.geeks.pixion.repositiories;

import com.geeks.pixion.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
