package com.geeks.pixion.services;

import com.geeks.pixion.payloads.UserAddDto;

import java.util.List;

public interface UserService {
    UserAddDto createUser(UserAddDto userAddDto);
    UserAddDto updateUser(UserAddDto userDto,String userId);
    UserAddDto getUserById(String userId);
    List<UserAddDto> getAllUsers();
    void deleteUser(String userId);
}
