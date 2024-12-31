package com.geeks.pixion.repositiories;
import com.geeks.pixion.entities.Category;
import com.geeks.pixion.entities.Post;
import com.geeks.pixion.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByApproved(boolean approved);
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    @Query("SELECT p FROM Post p ORDER BY RAND() LIMIT 1")
    Optional<Post> findRandomPost();
}
