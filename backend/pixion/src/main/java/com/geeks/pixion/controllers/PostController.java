package com.geeks.pixion.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geeks.pixion.exceptions.InvalidThrowException;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.ApiResponse;
import com.geeks.pixion.payloads.PostAddDto;
import com.geeks.pixion.payloads.PostResponseDto;
import com.geeks.pixion.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping(value = "/create",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PostResponseDto> createPost(@RequestPart("post") String post, @RequestPart("file") MultipartFile file) throws IOException, ResourceNotFoundException {
        ObjectMapper objectMapper=new ObjectMapper();
        PostAddDto postAddDto=objectMapper.readValue(post,PostAddDto.class);
        return new ResponseEntity<>(postService.createPost(postAddDto,file), HttpStatus.CREATED);
    }
    @PutMapping("/approve")
    public ResponseEntity<PostResponseDto> approvePost(@RequestParam Long postId) throws ResourceNotFoundException {
        return new ResponseEntity<>(postService.approvePost(postId),HttpStatus.OK);
    }
    @GetMapping("/byId/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId) throws ResourceNotFoundException {
       return new ResponseEntity<>(postService.findPostById(postId),HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<PostResponseDto>> findAllPost(){
        return new ResponseEntity<>(postService.findAllPost(),HttpStatus.OK);
    }
    @GetMapping("/pending-posts")
    public ResponseEntity<List<PostResponseDto>> pendingPosts(){
        return new ResponseEntity<>(postService.findAllPendingPosts(),HttpStatus.OK);
    }
    @GetMapping("/approved-posts")
    public ResponseEntity<List<PostResponseDto>> approvedPosts(){
        return new ResponseEntity<>(postService.findAllApprovedPosts(),HttpStatus.OK);
    }
    @DeleteMapping("/reject")
    public ApiResponse rejectPost(@RequestParam Long postId) throws ResourceNotFoundException {
        return postService.rejectPost(postId);
    }
    @DeleteMapping("/delete")
    public ApiResponse deletePost(@RequestParam Long postId) throws ResourceNotFoundException {
        return postService.deletePost(postId);
    }
    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadMedia(@RequestParam Long postId) throws ResourceNotFoundException, InvalidThrowException {
        return postService.downloadPostMedia(postId);
    }

    @GetMapping("/by/user")
    public ResponseEntity<List<PostResponseDto>> getPostsByUser(@RequestParam Long userId) throws ResourceNotFoundException {
        return new ResponseEntity<>(postService.getPostsByUser(userId),HttpStatus.OK);
    }
    @GetMapping("/by/category")
    public ResponseEntity<List<PostResponseDto>> getPostsByCategory(@RequestParam Long categoryId) throws ResourceNotFoundException {
        return new ResponseEntity<>(postService.getPostsByCategory(categoryId),HttpStatus.OK);
    }
}
