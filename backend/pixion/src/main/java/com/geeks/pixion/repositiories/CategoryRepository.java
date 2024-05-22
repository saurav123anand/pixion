package com.geeks.pixion.repositiories;

import com.geeks.pixion.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
