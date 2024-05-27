package com.geeks.pixion.repositiories;

import com.geeks.pixion.entities.Favorite;
import com.geeks.pixion.entities.Post;
import com.geeks.pixion.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
    boolean existsByPostAndUser(Post user, User post);
    List<Favorite> findAllByUser(User user);
    Optional<Favorite> findByPostAndUser(Post post, User user);
}
