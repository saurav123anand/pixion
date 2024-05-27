package com.geeks.pixion.utils;

import com.geeks.pixion.entities.Address;
import com.geeks.pixion.entities.Category;
import com.geeks.pixion.entities.User;
import com.geeks.pixion.exceptions.EmptyFieldException;
import com.geeks.pixion.exceptions.InvalidFieldValue;
import com.geeks.pixion.exceptions.InvalidThrowException;
import com.geeks.pixion.payloads.AddressDto;
import com.geeks.pixion.payloads.CategoryAddDto;
import com.geeks.pixion.payloads.UserUpdateDto;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.regex.Pattern;

@Component
public class Utils {
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
        if(user.getEmail()==null || userDto.getEmail().trim().isEmpty()){
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
    public String getFileNameFromUrl(String url) throws InvalidThrowException {
        try {
            URL urlObj = new URL(url);
            String path = urlObj.getPath();
            if (path == null || !path.contains("/")) {
                throw new InvalidThrowException("Invalid URL path");
            }
            return path.substring(path.lastIndexOf('/') + 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidThrowException("Failed to extract file name from URL"+e);
        }
    }

}
