package com.geeks.pixion.controllers;

import com.geeks.pixion.entities.Post;
import com.geeks.pixion.entities.User;
import com.geeks.pixion.exceptions.AlreadyExistsException;
import com.geeks.pixion.repositiories.PostRepository;
import com.geeks.pixion.services.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.geeks.pixion.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/posts")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private PostRepository postRepository;

    // Like a post
    @PostMapping("/{postId}/like")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @AuthenticationPrincipal User user) throws AlreadyExistsException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        likeService.likePost(user, post);  // Get user from @AuthenticationPrincipal
        return ResponseEntity.ok("Post liked successfully.");
    }

    // Unlike a post
    @DeleteMapping("/{postId}/unlike")
    public ResponseEntity<String> unlikePost(@PathVariable Long postId, @AuthenticationPrincipal User user) throws ResourceNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        likeService.unlikePost(user, post);  // Get user from @AuthenticationPrincipal
        return ResponseEntity.ok("Post unliked successfully.");
    }

    // Get like count for a post
    @GetMapping("/{postId}/like-count")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long postId) throws ResourceNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        long count = likeService.getLikeCount(post);
        return ResponseEntity.ok(count);
    }
}
