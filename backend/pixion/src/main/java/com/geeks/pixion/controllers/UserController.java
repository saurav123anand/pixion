package com.geeks.pixion.controllers;

import com.geeks.pixion.payloads.UserAddDto;
import com.geeks.pixion.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public UserAddDto createUser(@RequestBody UserAddDto userAddDto){
        return userService.createUser(userAddDto);
    }
}
