package com.geeks.pixion.controllers;

import com.geeks.pixion.entities.Favorite;
import com.geeks.pixion.exceptions.AlreadyExistsException;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.ApiResponse;
import com.geeks.pixion.payloads.FavoriteDto;
import com.geeks.pixion.payloads.FavoriteResponseDto;
import com.geeks.pixion.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/add")
    public FavoriteResponseDto addFavorite(@RequestBody FavoriteDto favoriteDto) throws ResourceNotFoundException, AlreadyExistsException {
        return favoriteService.addFavorite(favoriteDto);
    }

    @DeleteMapping("/remove")
    public ApiResponse removeFavorite(@RequestBody FavoriteDto favoriteDto) throws ResourceNotFoundException {
        return favoriteService.removeFavorite(favoriteDto);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteResponseDto>> getFavoritePostsByUser(@PathVariable Long userId) throws ResourceNotFoundException {
        List<FavoriteResponseDto> favoritePosts = favoriteService.getFavoritePostsByUser(userId);
        return ResponseEntity.ok(favoritePosts);
    }
}
