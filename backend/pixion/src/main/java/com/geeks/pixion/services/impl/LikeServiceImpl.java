package com.geeks.pixion.services.impl;

import com.geeks.pixion.entities.Like;
import com.geeks.pixion.entities.Post;
import com.geeks.pixion.entities.User;
import com.geeks.pixion.exceptions.AlreadyExistsException;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.repositiories.LikeRepository;
import com.geeks.pixion.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    public void likePost(User user, Post post) throws AlreadyExistsException {
        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);
        if (existingLike.isPresent()) {
            throw new AlreadyExistsException("User has already liked this post.");
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        likeRepository.save(like);
    }

    public void unlikePost(User user, Post post) throws ResourceNotFoundException {
        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);
        if (existingLike.isEmpty()) {
            throw new ResourceNotFoundException("Like not found.");
        }
        likeRepository.delete(existingLike.get());
    }

    public long getLikeCount(Post post) {
        return likeRepository.countByPost(post);
    }
}
