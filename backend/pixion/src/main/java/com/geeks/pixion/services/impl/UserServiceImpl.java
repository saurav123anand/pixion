package com.geeks.pixion.services.impl;
import com.geeks.pixion.entities.User;
import com.geeks.pixion.exceptions.EmptyFieldException;
import com.geeks.pixion.exceptions.InvalidFieldValue;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.*;
import com.geeks.pixion.repositiories.AddressRepository;
import com.geeks.pixion.repositiories.UserRepository;
import com.geeks.pixion.services.UserService;
import com.geeks.pixion.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private Utils utils;

    @Override
    public UserResponseDto createUser(UserAddDto userAddDto) {
        User user = modelMapper.map(userAddDto, User.class);
        user.setCreatedTimeStamp(new Date());
        User saved = userRepository.save(user);
        return modelMapper.map(saved,UserResponseDto.class);
    }

    @Override
    public UserResponseDto updateUser(UserUpdateDto userDto, Long userId) throws InvalidFieldValue, EmptyFieldException, ResourceNotFoundException {
        User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found for user id "+userId));
        user=utils.validateAndSetFieldValue(userDto,user);
        userRepository.save(user);
//        return utils.convertUserToUserResponse(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto getUserById(Long userId) throws ResourceNotFoundException {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found for user id "+userId));
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
       List<User> users= userRepository.findAll();
       return users.stream().map(user -> modelMapper.map(user,UserResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public ApiResponse deleteUser(Long userId) throws ResourceNotFoundException {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found for user id "+userId));
        userRepository.delete(user);
        ApiResponse apiResponse=new ApiResponse("User sucessfully deleted for user id"+userId,true, HttpStatus.OK.value());
        return apiResponse;
    }

    public UserPaginationResponse getUsersByPages(Integer pageNumber,Integer pageSize){
        Pageable pageable= PageRequest.of(pageNumber,pageSize);
        Page<User> pageUsers = userRepository.findAll(pageable);
        List<User> allUsersOfPage = pageUsers.getContent();
        List<UserResponseDto> users = allUsersOfPage.stream().map(user -> modelMapper.map(user, UserResponseDto.class)).collect(Collectors.toList());
        UserPaginationResponse userPaginationResponse=new UserPaginationResponse();
        userPaginationResponse.setUsers(users);
        userPaginationResponse.setPageNumber(pageUsers.getNumber());
        userPaginationResponse.setPageSize(pageUsers.getSize());
        userPaginationResponse.setTotalElements(pageUsers.getTotalElements());
        userPaginationResponse.setTotalPages(pageUsers.getTotalPages());
        userPaginationResponse.setLastPage(pageUsers.isLast());
        return userPaginationResponse;
    }

    @Override
    public UserPaginationResponse getSortedUsersByPages(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<User> pageUsers = userRepository.findAll(pageable);
        List<User> allUsersOfPage = pageUsers.getContent();
        List<UserResponseDto> users = allUsersOfPage.stream().map(user -> modelMapper.map(user, UserResponseDto.class)).collect(Collectors.toList());
        UserPaginationResponse userPaginationResponse=new UserPaginationResponse();
        userPaginationResponse.setUsers(users);
        userPaginationResponse.setPageNumber(pageUsers.getNumber());
        userPaginationResponse.setPageSize(pageUsers.getSize());
        userPaginationResponse.setTotalElements(pageUsers.getTotalElements());
        userPaginationResponse.setTotalPages(pageUsers.getTotalPages());
        userPaginationResponse.setLastPage(pageUsers.isLast());
        return userPaginationResponse;
    }

    @Override
    public List<UserResponseDto> searchUsers(String keyword) {
        List<User> users = userRepository.findByFirstNameContaining(keyword);
        return users.stream().map(user -> modelMapper.map(user,UserResponseDto.class)).collect(Collectors.toList());
    }

}
