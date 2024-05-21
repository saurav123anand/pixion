package com.geeks.pixion.controllers;

import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.*;
import com.geeks.pixion.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserAddDto userAddDto){
        UserResponseDto user = userService.createUser(userAddDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public UserResponseDto updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto, @RequestParam Long userId) throws Exception {
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
        ApiResponse response=userService.deleteUser(userId);
        return response;
    }

    // pagination
    @GetMapping("/pagination")
    public UserPaginationResponse findAllByPages(@RequestParam(value = "pageNumber",defaultValue ="0",required = false)
            Integer pageNumber, @RequestParam(value = "pageSize",defaultValue ="10",required = false) Integer pageSize ) throws ResourceNotFoundException {
        return userService.getUsersByPages(pageNumber,pageSize);
    }

    // pagination with sorting
    @GetMapping("/pagination/sorted")
    public UserPaginationResponse findAllSortedByPages(@RequestParam(value = "pageNumber",defaultValue ="0",required = false)
                                                 Integer pageNumber, @RequestParam(value = "pageSize",defaultValue ="10",required = false) Integer pageSize,
                                                       @RequestParam(value = "sortBy",defaultValue ="userId",required = false)
                                                       String sortBy, @RequestParam(value = "sortDir",defaultValue ="asc",required = false) String sortDir) throws ResourceNotFoundException {
        return userService.getSortedUsersByPages(pageNumber,pageSize,sortBy,sortDir);
    }
}
