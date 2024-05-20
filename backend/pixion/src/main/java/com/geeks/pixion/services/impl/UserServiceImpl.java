package com.geeks.pixion.services.impl;

import com.geeks.pixion.entities.Address;
import com.geeks.pixion.entities.User;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.AddressDto;
import com.geeks.pixion.payloads.UserAddDto;
import com.geeks.pixion.payloads.UserResponseDto;
import com.geeks.pixion.payloads.UserUpdateDto;
import com.geeks.pixion.repositiories.AddressRepository;
import com.geeks.pixion.repositiories.UserRepository;
import com.geeks.pixion.services.UserService;
import com.geeks.pixion.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        User saved = userRepository.save(user);
        return modelMapper.map(saved,UserResponseDto.class);
    }

    @Override
    public UserResponseDto updateUser(UserUpdateDto userDto, Long userId) throws ResourceNotFoundException {
        User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found for user id "+userId));

        if (userDto.getAddress()!=null){
            Address address=user.getAddress();
            if(address!=null){
                // update existing address
                address.setCity(userDto.getAddress().getCity());
                address.setStreet(userDto.getAddress().getStreet());
                address.setCountry(userDto.getAddress().getCountry());
            }
            else{
                // add new address
                AddressDto addressDto =userDto.getAddress();
                Address newAddress=new Address();
                newAddress.setUser(user);
                newAddress.setCity(addressDto.getCity());
                newAddress.setStreet(addressDto.getStreet());
                newAddress.setCountry(addressDto.getCountry());
                user.setAddress(newAddress);
            }
        }
        User save = userRepository.save(user);
        return utils.convertUserToUserResponse(user);
    }

    @Override
    public UserResponseDto getUserById(Long userId) throws ResourceNotFoundException {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found for user id "+userId));
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
       List<User> users= userRepository.findAll();
       return users.stream().map(user -> utils.convertUserToUserResponse(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) throws ResourceNotFoundException {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found for user id "+userId));
        userRepository.delete(user);
    }
}
