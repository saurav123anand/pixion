package com.geeks.pixion.services.impl;
import com.geeks.pixion.constants.Constants;
import com.geeks.pixion.entities.User;
import com.geeks.pixion.exceptions.AlreadyExistsException;
import com.geeks.pixion.exceptions.EmptyFieldException;
import com.geeks.pixion.exceptions.InvalidFieldValue;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.*;
import com.geeks.pixion.repositiories.UserRepository;
import com.geeks.pixion.services.S3Service;
import com.geeks.pixion.services.UserService;
import com.geeks.pixion.utils.Utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Utils utils;
    @Value("${s3.fileRoot1}")
    private String fileRoot1;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto createUser(UserAddDto userAddDto) throws AlreadyExistsException {
        Optional<User> user=userRepository.findByUsername(userAddDto.getUsername());
        if(user.isPresent()){
            throw new AlreadyExistsException("user already present for username "+userAddDto.getUsername());
        }
        User mappedUser = modelMapper.map(userAddDto, User.class);
        mappedUser.setPassword(passwordEncoder.encode(userAddDto.getPassword()));
        mappedUser.setRole(userAddDto.getRole());
        mappedUser = userRepository.save(mappedUser);
        return modelMapper.map(mappedUser,UserResponseDto.class);
    }

    @Override
    public UserResponseDto updateUser(UserUpdateDto userDto, Long userId) throws InvalidFieldValue, EmptyFieldException, ResourceNotFoundException{
        User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException(Constants.USER_EXCEPTION_MSG +userId));
        user=utils.validateAndSetFieldValue(userDto,user);
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto getUserById(Long userId) throws ResourceNotFoundException {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException(Constants.USER_EXCEPTION_MSG+userId));
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
        String imageKey=user.getProfileImageName().substring(user.getProfileImageName().lastIndexOf("/")+1);
        s3Service.deleteImage(imageKey,fileRoot1);
        userRepository.delete(user);
        return new ApiResponse("User successfully deleted for user id"+userId,true, HttpStatus.OK.value());
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

    public UserResponseDto imageUploadToS3AndUpdateUser(Long userId,MultipartFile image) throws ResourceNotFoundException, IOException {
        User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found for user id "+userId));
        if(image!=null && !image.isEmpty()){
            String key=image.getOriginalFilename();
            assert key != null;
            key=key.replaceAll(" ","");
            String modifiedFileName = UUID.randomUUID().toString() + key.substring(0,key.lastIndexOf(".")) + key.substring(key.lastIndexOf("."));
            String imageUrl= s3Service.uploadMediaToS3(modifiedFileName,image.getInputStream(),image.getSize(),image.getContentType(),fileRoot1);
            user.setProfileImageName(imageUrl);
        }
        userRepository.save(user);
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Transactional
    @Override
    public void followUser(Long userId, Long followerId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(Constants.USER_EXCEPTION_MSG + userId));
        User follower = userRepository.findById(followerId).orElseThrow(() -> new ResourceNotFoundException("followers not found for followerId" + followerId));
        user.getFollowers().add(follower);
        follower.getFollowing().add(user);
        userRepository.save(user);
        userRepository.save(follower);
    }

    @Transactional
    @Override
    public void unfollowUser(Long userId, Long followerId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(Constants.USER_EXCEPTION_MSG + userId));
        User following = userRepository.findById(followerId).orElseThrow(() -> new ResourceNotFoundException("followers not found for followerId" + followerId));
        user.getFollowers().remove(following);
        following.getFollowing().remove(user);
        userRepository.save(user);
        userRepository.save(following);
    }
    @Transactional(readOnly = true)
    @Override
    public Map<String,Object> getFollowersAndFollowing(Long userId) throws ResourceNotFoundException {
        Map<String,Object> result=new HashMap<>();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(Constants.USER_EXCEPTION_MSG + userId));
        Set<User> followers = user.getFollowers();
        Set<User> following = user.getFollowing();
        result.put("followers", followers);
        result.put("following", following);
        result.put("followersCount", followers.size());
        result.put("followingCount", following.size());
        return result;
    }

}
