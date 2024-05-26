package com.geeks.pixion.services.impl;
import com.amazonaws.services.s3.AmazonS3;
import com.geeks.pixion.entities.User;
import com.geeks.pixion.exceptions.EmptyFieldException;
import com.geeks.pixion.exceptions.InvalidFieldValue;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.*;
import com.geeks.pixion.repositiories.UserRepository;
import com.geeks.pixion.services.S3Service;
import com.geeks.pixion.services.UserService;
import com.geeks.pixion.utils.Utils;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Utils utils;
    @Autowired
    private AmazonS3 amazonS3;
    @Value("${s3.bucketName}")
    private String bucketName;
    @Value("${s3.fileRoot1}")
    private String fileRoot1;
    @Value("${s3.region}")
    private String region;
    @Autowired
    private S3Service s3Service;

    @Override
    public UserResponseDto createUser(UserAddDto userAddDto) {
        User user = modelMapper.map(userAddDto, User.class);
        user.setCreatedTimeStamp(new Date());
        //user.setProfileImageName();
        User saved = userRepository.save(user);
        return modelMapper.map(saved,UserResponseDto.class);
    }

    @Override
    public UserResponseDto updateUser(UserUpdateDto userDto, Long userId) throws InvalidFieldValue, EmptyFieldException, ResourceNotFoundException{
        User user= userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found for user id "+userId));
        user=utils.validateAndSetFieldValue(userDto,user);
        userRepository.save(user);
//        if(image!=null && !image.isEmpty()){
//            String key=image.getOriginalFilename();
//            String imageUrl=uploadImage(key,image.getInputStream(),image.getSize(),image.getContentType());
//            user.setProfileImageName(imageUrl);
//        }
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
        String imageKey=user.getProfileImageName().substring(user.getProfileImageName().lastIndexOf("/")+1);
        System.out.println("image key is "+imageKey);
        s3Service.deleteImage(imageKey,fileRoot1);
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

//    @Override
//    public Map<String,String> uploadUserImageToS3(MultipartFile file,String key) throws IOException {
//        String contentType= file.getContentType();
//        Map<String,String> map=new HashMap<>();
//        // upload the file directly to s3 using the input stream
//        try(InputStream inputStream=file.getInputStream()){
//            String s3Url=uploadImage(key,inputStream,file.getSize(),contentType);
//            String customUrl="http://s3.com/images/"+key;
//            map.put("s3Url",s3Url);
//            map.put("customUrl",customUrl);
//            return map;
//        }
//    }
    
//    public String uploadImage(String key,InputStream inputStream,long contentLength,String contentType){
//        ObjectMetadata metadata=new ObjectMetadata();
//        metadata.setContentLength(contentLength);
//        metadata.setContentType(contentType);
//        PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName,fileRoot1+"/"+key,inputStream,metadata)
//                .withCannedAcl(CannedAccessControlList.BucketOwnerFullControl);
//        amazonS3.putObject(putObjectRequest);
//        return getS3Url(key);
//    }
//    public String getS3Url(String key){
//        return String.format("https://%s.s3.%s.amazonaws.com/%s",bucketName,region,fileRoot1+"/"+key);
//    }
//    public void deleteImage(String key){
//        System.out.println(bucketName+fileRoot1+"/"+key);
//        amazonS3.deleteObject(new DeleteObjectRequest(bucketName,fileRoot1+"/"+key));
//    }

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

}
