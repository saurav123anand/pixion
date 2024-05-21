package com.geeks.pixion.controllers;

import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.ApiResponse;
import com.geeks.pixion.payloads.UserAddDto;
import com.geeks.pixion.payloads.UserResponseDto;
import com.geeks.pixion.payloads.UserUpdateDto;
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
    @GetMapping("/")
    public List<UserResponseDto> findAllByPages(@RequestParam(value = "pageNumber",defaultValue ="1",required = false)
            Integer pageNumber,@RequestParam(value = "pageSize",defaultValue ="3",required = false) Integer pageSize ) throws ResourceNotFoundException {
        return userService.getUsersByPages(pageNumber,pageSize);
    }
}
