package com.geeks.pixion.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.geeks.pixion.constants.Constants;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.*;
import com.geeks.pixion.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AmazonS3 s3Service;

    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserAddDto userAddDto){
        UserResponseDto user = userService.createUser(userAddDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PutMapping(value = "/update/{userId}",consumes = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public UserResponseDto updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto, @PathVariable Long userId) throws Exception {
        return userService.updateUser(userUpdateDto,userId);
    }
    @GetMapping("/all")
    public List<UserResponseDto> findAll() throws ResourceNotFoundException {
        return userService.getAllUsers();
    }
    @GetMapping("/byId/{userId}")
    public UserResponseDto findById(@PathVariable Long userId) throws ResourceNotFoundException {
        return userService.getUserById(userId);
    }
    @DeleteMapping("/byId/{userId}")
    public ApiResponse deleteById(@PathVariable Long userId) throws ResourceNotFoundException {
        return userService.deleteUser(userId);
    }

    // pagination
    @GetMapping("/pagination")
    public UserPaginationResponse findAllByPages(@RequestParam(value = "pageNumber",defaultValue = Constants.PAGE_NUMBER,required = false)
            Integer pageNumber, @RequestParam(value = "pageSize",defaultValue =Constants.PAGE_SIZE,required = false) Integer pageSize ) throws ResourceNotFoundException {
        return userService.getUsersByPages(pageNumber,pageSize);
    }

    // pagination with sorting
    @GetMapping("/pagination/sorted")
    public UserPaginationResponse findAllSortedByPages(@RequestParam(value = "pageNumber",defaultValue = Constants.PAGE_NUMBER,required = false)
                                                 Integer pageNumber, @RequestParam(value = "pageSize",defaultValue =Constants.PAGE_SIZE,required = false) Integer pageSize,
                                                       @RequestParam(value = "sortBy",defaultValue =Constants.SORT_BY,required = false)
                                                       String sortBy, @RequestParam(value = "sortDir",defaultValue =Constants.SORT_DIR,required = false) String sortDir) throws ResourceNotFoundException {
        return userService.getSortedUsersByPages(pageNumber,pageSize,sortBy,sortDir);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserResponseDto>> searchPostByFirstName(@PathVariable String keywords) {
        List<UserResponseDto> response = userService.searchUsers(keywords);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/upload/{userId}")
    public UserResponseDto uploadImage(@PathVariable Long userId,@RequestParam("image") MultipartFile image) throws IOException, ResourceNotFoundException {
         return userService.imageUploadToS3AndUpdateUser(userId,image);
    }
}
