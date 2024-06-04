package com.geeks.pixion.controllers;

import com.geeks.pixion.entities.AuthenticationRequest;
import com.geeks.pixion.entities.AuthenticationResponse;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.services.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) throws ResourceNotFoundException {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
