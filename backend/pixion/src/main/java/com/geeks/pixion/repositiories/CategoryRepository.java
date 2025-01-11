package com.geeks.pixion.repositiories;

import com.geeks.pixion.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryTitleContainingIgnoreCase(String categoryTitle);
}
