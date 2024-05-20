package com.geeks.pixion.utils;

import com.geeks.pixion.entities.User;
import com.geeks.pixion.payloads.AddressDto;
import com.geeks.pixion.payloads.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    public UserResponseDto convertUserToUserResponse(User user){
        UserResponseDto userResponseDto=new UserResponseDto();
        AddressDto addressDto=new AddressDto();
        addressDto.setStreet(user.getAddress().getStreet());
        addressDto.setCity(user.getAddress().getCity());
        addressDto.setCountry(user.getAddress().getCountry());
        userResponseDto.setAddress(addressDto);
        userResponseDto.setUserId(user.getUserId());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFollowers(user.getFollowers());
        userResponseDto.setFollowing(user.getFollowing());
        userResponseDto.setAllTimeRank(user.getAllTimeRank());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setLast30DaysRank(user.getLast30DaysRank());
        userResponseDto.setProfileImageName(user.getProfileImageName());
        return userResponseDto;
    }
}
