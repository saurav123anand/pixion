package com.geeks.pixion.controllers;

import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.UserAddDto;
import com.geeks.pixion.payloads.UserResponseDto;
import com.geeks.pixion.payloads.UserUpdateDto;
import com.geeks.pixion.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public UserResponseDto createUser(@Valid @RequestBody UserAddDto userAddDto){
        return userService.createUser(userAddDto);
    }
    @PutMapping("/update")
    public UserResponseDto updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto, @RequestParam Long userId) throws Exception {
        return userService.updateUser(userUpdateDto,userId);
    }
    @GetMapping("/")
    public List<UserResponseDto> findAll() throws ResourceNotFoundException {
        return userService.getAllUsers();
    }
    @GetMapping("/byId/{userId}")
    public UserResponseDto findById(@PathVariable Long userid) throws ResourceNotFoundException {
        return userService.getUserById(userid);
    }
    @DeleteMapping("/byId/{userId}")
    public void deleteById(@PathVariable Long userid) throws ResourceNotFoundException {
        userService.deleteUser(userid);
    }
}
