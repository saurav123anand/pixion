package com.geeks.pixion.services.impl;

import com.geeks.pixion.entities.User;
import com.geeks.pixion.payloads.UserAddDto;
import com.geeks.pixion.repositiories.UserRepository;
import com.geeks.pixion.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserAddDto createUser(UserAddDto userAddDto) {
        User user = modelMapper.map(userAddDto, User.class);
        User saved = userRepository.save(user);
        return modelMapper.map(saved,UserAddDto.class);
    }

    @Override
    public UserAddDto updateUser(UserAddDto userDto, String userId) {
        return null;
    }

    @Override
    public UserAddDto getUserById(String userId) {
        return null;
    }

    @Override
    public List<UserAddDto> getAllUsers() {
        return List.of();
    }

    @Override
    public void deleteUser(String userId) {

    }
}
