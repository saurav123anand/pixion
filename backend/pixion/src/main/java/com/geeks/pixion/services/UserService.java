package com.geeks.pixion.services;

import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.UserAddDto;
import com.geeks.pixion.payloads.UserResponseDto;
import com.geeks.pixion.payloads.UserUpdateDto;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserAddDto userAddDto);
    UserResponseDto updateUser(UserUpdateDto userDto, Long userId) throws ResourceNotFoundException;
    UserResponseDto getUserById(Long userId) throws ResourceNotFoundException;
    List<UserResponseDto> getAllUsers();
    void deleteUser(Long userId) throws ResourceNotFoundException;
}
