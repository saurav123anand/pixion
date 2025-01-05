package com.geeks.pixion.repositiories;

import com.geeks.pixion.entities.Like;
import com.geeks.pixion.entities.Post;
import com.geeks.pixion.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Optional<Like> findByUserAndPost(User user, Post post);

    long countByPost(Post post);
    void deleteByPost(Post post);
}
