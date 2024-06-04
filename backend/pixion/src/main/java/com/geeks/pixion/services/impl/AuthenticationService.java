package com.geeks.pixion.services.impl;

import com.geeks.pixion.entities.AuthenticationRequest;
import com.geeks.pixion.entities.AuthenticationResponse;
import com.geeks.pixion.entities.User;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.repositiories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

// THIS CLASS IS FOR HANDLING THE LOGIN AND REGISTER
@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws ResourceNotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        ));
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()->new ResourceNotFoundException("user not found for username "+request.getUsername()));
        String token= jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }




}
