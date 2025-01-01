package com.geeks.pixion.services;

import com.geeks.pixion.entities.Post;
import com.geeks.pixion.entities.User;
import com.geeks.pixion.exceptions.AlreadyExistsException;
import com.geeks.pixion.exceptions.ResourceNotFoundException;

public interface LikeService {
    void likePost(User user, Post post) throws AlreadyExistsException;
    void unlikePost(User user, Post post) throws ResourceNotFoundException;
    long getLikeCount(Post post);
}
