package com.geeks.pixion.services;
import com.geeks.pixion.exceptions.AlreadyExistsException;
import com.geeks.pixion.exceptions.EmptyFieldException;
import com.geeks.pixion.exceptions.InvalidFieldValue;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
    UserResponseDto createUser(UserAddDto userAddDto) throws AlreadyExistsException;
    UserResponseDto updateUser(UserUpdateDto userDto, Long userId) throws ResourceNotFoundException, InvalidFieldValue, EmptyFieldException;
    UserResponseDto getUserById(Long userId) throws ResourceNotFoundException;
    List<UserResponseDto> getAllUsers();
    ApiResponse deleteUser(Long userId) throws ResourceNotFoundException;
    UserPaginationResponse getUsersByPages(Integer pageNumber, Integer pageSize);
    UserPaginationResponse getSortedUsersByPages(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
    List<UserResponseDto> searchUsers(String keyword);
    UserResponseDto imageUploadToS3AndUpdateUser(Long userId,MultipartFile image) throws ResourceNotFoundException, IOException;

    void followUser(Long userId,Long followerId) throws ResourceNotFoundException;
    void unfollowUser(Long userId,Long followerId) throws ResourceNotFoundException;

    Map<String,Object> getFollowersAndFollowing(Long userId) throws ResourceNotFoundException;

}
