package com.geeks.pixion.repositiories;
import com.geeks.pixion.constants.PostType;
import com.geeks.pixion.entities.Category;
import com.geeks.pixion.entities.Post;
import com.geeks.pixion.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByApproved(boolean approved);
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    @Query("SELECT p FROM Post p WHERE p.approved = true AND p.postType = :type ORDER BY RAND() limit 1")
    Optional<Post> findRandomPostByType(@Param("type") PostType type);
    List<Post> findByCategoryIn(List<Category> categories);
}
