package com.geeks.pixion.services;

import com.geeks.pixion.exceptions.EmptyFieldException;
import com.geeks.pixion.exceptions.InvalidFieldValue;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.*;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserAddDto userAddDto);
    UserResponseDto updateUser(UserUpdateDto userDto, Long userId) throws ResourceNotFoundException, InvalidFieldValue, EmptyFieldException;
    UserResponseDto getUserById(Long userId) throws ResourceNotFoundException;
    List<UserResponseDto> getAllUsers();
    ApiResponse deleteUser(Long userId) throws ResourceNotFoundException;
    UserPaginationResponse getUsersByPages(Integer pageNumber, Integer pageSize);
    UserPaginationResponse getSortedUsersByPages(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
}
