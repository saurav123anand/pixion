package com.geeks.pixion.services;

import com.geeks.pixion.exceptions.AlreadyExistsException;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.ApiResponse;
import com.geeks.pixion.payloads.FavoriteDto;
import com.geeks.pixion.payloads.FavoriteResponseDto;

import java.util.List;

public interface FavoriteService {

    FavoriteResponseDto addFavorite(FavoriteDto favoriteDto) throws ResourceNotFoundException, AlreadyExistsException;
    ApiResponse removeFavorite(FavoriteDto favoriteDto) throws ResourceNotFoundException;
    List<FavoriteResponseDto> getFavoritePostsByUser(Long userId) throws ResourceNotFoundException;
}
