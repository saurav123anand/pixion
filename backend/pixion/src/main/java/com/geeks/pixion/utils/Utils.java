package com.geeks.pixion.utils;

import com.geeks.pixion.entities.Address;
import com.geeks.pixion.entities.Category;
import com.geeks.pixion.entities.User;
import com.geeks.pixion.exceptions.EmptyFieldException;
import com.geeks.pixion.exceptions.InvalidFieldValue;
import com.geeks.pixion.payloads.AddressDto;
import com.geeks.pixion.payloads.CategoryAddDto;
import com.geeks.pixion.payloads.UserResponseDto;
import com.geeks.pixion.payloads.UserUpdateDto;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.regex.Pattern;

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
    public User validateAndSetFieldValue(UserUpdateDto userDto,User user) throws EmptyFieldException, InvalidFieldValue {
        if(userDto.getFirstName().trim().isEmpty()){
            throw new EmptyFieldException("firstName is empty");
        }
        else{
            if(userDto.getFirstName().length()<4){
                throw new InvalidFieldValue("firstName should have at least 4 characters");
            }
            else{
                user.setFirstName(userDto.getFirstName());
            }
        }
        user.setLastName(userDto.getLastName());
        if(userDto.getEmail().trim().isEmpty()){
            throw new EmptyFieldException("email is empty");
        }
        else{
            if(!Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
                    .matcher(userDto.getEmail())
                    .matches()){
                throw new InvalidFieldValue("Email is invalid");
            }
            else{
                user.setEmail(userDto.getEmail());
            }
        }
        if (userDto.getAddress()!=null){
            Address address=user.getAddress();
            if(address!=null){
                // update existing address
                address.setCity(userDto.getAddress().getCity());
                address.setStreet(userDto.getAddress().getStreet());
                address.setCountry(userDto.getAddress().getCountry());
                address.setZipCode(userDto.getAddress().getZipCode());
            }
            else{
                // add new address
                AddressDto addressDto =userDto.getAddress();
                Address newAddress=new Address();
                newAddress.setUser(user);
                newAddress.setCity(addressDto.getCity());
                newAddress.setStreet(addressDto.getStreet());
                newAddress.setCountry(addressDto.getCountry());
                newAddress.setZipCode(addressDto.getZipCode());
                user.setAddress(newAddress);
            }
        }
        user.setPassword(userDto.getPassword());
        user.setBio(userDto.getBio());
        user.setProfileImageName(userDto.getProfileImageName());
        user.setInstaUrl(userDto.getInstaUrl());
        user.setXurl(userDto.getXurl());
        user.setPortfolioUrl(userDto.getPortfolioUrl());
        user.setLinkedinUrl(userDto.getLinkedinUrl());
        return user;
    }

    public Category validateAndSetFieldValue(CategoryAddDto request, Category category){
        if(!request.getTitle().trim().isEmpty() &&  !request.getTitle().equals(category.getCategoryTitle())){
            category.setCategoryTitle(request.getTitle());
        }
        if(!request.getDescription().trim().isEmpty() && !request.getDescription().equals(category.getCategoryDescription())){
            category.setCategoryDescription(request.getDescription());
        }

        return  category;
    }
    public String getFileNameFromUrl(String url) {
        try {
            URL urlObj = new URL(url);
            String path = urlObj.getPath();
            if (path == null || path.isEmpty() || !path.contains("/")) {
                throw new RuntimeException("Invalid URL path");
            }
            return path.substring(path.lastIndexOf('/') + 1);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract file name from URL", e);
        }
    }

}
