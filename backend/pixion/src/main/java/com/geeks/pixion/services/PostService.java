package com.geeks.pixion.services;

import com.geeks.pixion.exceptions.InvalidThrowException;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.ApiResponse;
import com.geeks.pixion.payloads.PostAddDto;
import com.geeks.pixion.payloads.PostResponseDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    PostResponseDto createPost(PostAddDto postAddDto, MultipartFile file) throws ResourceNotFoundException, IOException;
    PostResponseDto findPostById(Long postId) throws ResourceNotFoundException;
    List<PostResponseDto> findAllPost();
    PostResponseDto approvePost(Long postId) throws ResourceNotFoundException;
    ApiResponse rejectPost(Long postId) throws ResourceNotFoundException;
    List<PostResponseDto> findAllPendingPosts();
    List<PostResponseDto> findAllApprovedPosts();
    ApiResponse deletePost(Long postId) throws ResourceNotFoundException;
    List<PostResponseDto> getPostsByUser(Long userId) throws ResourceNotFoundException;
    List<PostResponseDto> getPostsByCategory(Long categoryId) throws ResourceNotFoundException;
    ResponseEntity<InputStreamResource> downloadPostMedia(Long postId) throws ResourceNotFoundException, InvalidThrowException;
}
