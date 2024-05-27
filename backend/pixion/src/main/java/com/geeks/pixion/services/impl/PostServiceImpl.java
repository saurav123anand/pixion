package com.geeks.pixion.services.impl;

import com.geeks.pixion.constants.Constants;
import com.geeks.pixion.constants.PostType;
import com.geeks.pixion.entities.Category;
import com.geeks.pixion.entities.Post;
import com.geeks.pixion.entities.User;
import com.geeks.pixion.exceptions.InvalidThrowException;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.ApiResponse;
import com.geeks.pixion.payloads.PostAddDto;
import com.geeks.pixion.payloads.PostResponseDto;
import com.geeks.pixion.repositiories.CategoryRepository;
import com.geeks.pixion.repositiories.PostRepository;
import com.geeks.pixion.repositiories.UserRepository;
import com.geeks.pixion.services.EmailService;
import com.geeks.pixion.services.PostService;
import com.geeks.pixion.services.S3Service;
import com.geeks.pixion.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${s3.fileRoot2}")
    private String fileRoot2;
    @Value("${s3.fileRoot3}")
    private String fileRoot3;
    @Value("${spring.mail.username}")
    private String from;
    @Value("${spring.mail.username}")
    private String cc;
    @Value("${mail.senderName}")
    private String senderName;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private EmailService emailService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Utils utils;
    @Override
    public PostResponseDto createPost(PostAddDto postAddDto, MultipartFile file) throws ResourceNotFoundException, IOException {
        // at later time get the user directly , fetch the currently logged in user from security context instead.
        User user = userRepository.findById(postAddDto.getUser().getUserId()).orElseThrow(() -> new ResourceNotFoundException("user not found for user id " + postAddDto.getUser().getUserId()));
        Category category = categoryRepository.findById(postAddDto.getCategory().getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("category not found for category id " + postAddDto.getCategory().getCategoryId()));
        Post post=new Post();
        post.setPostCreationTime(new Date());
        post.setContent(postAddDto.getContent());
        post.setTitle(postAddDto.getTitle());
        post.setUser(user);
        post.setCategory(category);
        post.setLocation(postAddDto.getLocation());
        post.setPostType(postAddDto.getPostType());
        if(file!=null && !file.isEmpty()){
            String key=file.getOriginalFilename();
            assert key != null;
            key=key.replaceAll(" ","");
            String modifiedFileName = UUID.randomUUID().toString() + key.substring(0,key.lastIndexOf(".")) + key.substring(key.lastIndexOf("."));
            String mediaUrl=null;
            if(postAddDto.getPostType().equals(PostType.IMAGE)){
                mediaUrl= s3Service.uploadMediaToS3(modifiedFileName,file.getInputStream(),file.getSize(),file.getContentType(),fileRoot2);
            }
            else{
                mediaUrl= s3Service.uploadMediaToS3(modifiedFileName,file.getInputStream(),file.getSize(),file.getContentType(),fileRoot3);
            }
            post.setMediaUrl(mediaUrl);
        }
        Post savedPost = postRepository.save(post);
        emailService.sendEmailWithAttachment(from,new String[]{user.getEmail()},new String[]{cc},"Post sent for Approval",senderName, user.getFirstName(),file);
        return modelMapper.map(savedPost,PostResponseDto.class);
    }
    @Override
    public PostResponseDto findPostById(Long postId) throws ResourceNotFoundException {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(Constants.POST_EXCEPTION_MSG + postId));
        return modelMapper.map(post, PostResponseDto.class);
    }

    @Override
    public List<PostResponseDto> findAllPost() {
        List<Post> allPosts = postRepository.findAll();
        return allPosts.stream().map(post -> modelMapper.map(post, PostResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public PostResponseDto approvePost(Long postId) throws ResourceNotFoundException {
        Post post=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(Constants.POST_EXCEPTION_MSG + postId));
        User user = userRepository.findById(post.getUser().getUserId()).orElseThrow(() -> new ResourceNotFoundException("user not found for post id " + post.getUser().getUserId()));
        post.setApproved(true);
        Post saved=postRepository.save(post);
        String attachmentName=post.getMediaUrl().substring(post.getMediaUrl().lastIndexOf("/")+1);
        emailService.sendApprovedRejectPostEmail(from,new String[]{user.getEmail()},new String[]{cc},
                "Post Approved",senderName,user.getFirstName(),attachmentName,post.getMediaUrl(),"APPROVED");
        return modelMapper.map(saved, PostResponseDto.class);
    }
    @Override
    public ApiResponse rejectPost(Long postId) throws ResourceNotFoundException {
        Post post=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(Constants.POST_EXCEPTION_MSG + postId));
        User user = userRepository.findById(post.getUser().getUserId()).orElseThrow(() -> new ResourceNotFoundException("user not found for post id " + post.getUser().getUserId()));
        PostType postType = post.getPostType();
        String key=post.getMediaUrl().substring(post.getMediaUrl().lastIndexOf("/")+1);
        String attachmentName=post.getMediaUrl().substring(post.getMediaUrl().lastIndexOf("/")+1);
        emailService.sendApprovedRejectPostEmail(from,new String[]{user.getEmail()},new String[]{cc},
                "Post Rejected",senderName,user.getFirstName(),attachmentName,post.getMediaUrl(),"REJECTED");
        postRepository.deleteById(postId);
        if (postType == PostType.IMAGE) {
            s3Service.deleteImage(key, fileRoot2);
        } else {
            s3Service.deleteImage(key, fileRoot3);
        }
        return new ApiResponse("Post Rejected and deleted for postId "+postId,true, HttpStatus.OK.value());
    }

    @Override
    public List<PostResponseDto> findAllPendingPosts(){
        List<Post> pendingPosts = postRepository.findByApproved(false);
        return pendingPosts.stream().map(post -> modelMapper.map(post,PostResponseDto.class)).collect(Collectors.toList());
    }
    @Override
    public List<PostResponseDto> findAllApprovedPosts(){
        List<Post> pendingPosts = postRepository.findByApproved(true);
        return pendingPosts.stream().map(post -> modelMapper.map(post,PostResponseDto.class)).collect(Collectors.toList());
    }
    @Override
    public List<PostResponseDto> getPostsByCategory(Long categoryId) throws ResourceNotFoundException {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found for categoryId "+categoryId));
        List<Post> posts = postRepository.findByCategory(category);
        return posts.stream().map(post -> modelMapper.map(post, PostResponseDto.class)).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadPostMedia(Long postId) throws ResourceNotFoundException, InvalidThrowException {
        Post post=postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException(Constants.POST_EXCEPTION_MSG+postId));
        String mediaUrl = post.getMediaUrl();
        ResponseEntity<byte[]> response = restTemplate.getForEntity(mediaUrl, byte[].class);
        byte[] mediaData = response.getBody();
        // Prepare the response entity
        InputStream inputStream = new ByteArrayInputStream(mediaData);
        HttpHeaders headers = new HttpHeaders();
        // Extract the file name from the URL
        String fileName = utils.getFileNameFromUrl(mediaUrl);
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // Set to generic binary data
        headers.setContentLength(Objects.requireNonNull(mediaData).length);
        return new ResponseEntity<>(new InputStreamResource(inputStream),headers,HttpStatus.OK);
    }

    @Override
    public List<PostResponseDto> getPostsByUser(Long userId) throws ResourceNotFoundException {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException(Constants.USER_EXCEPTION_MSG+userId));
        List<Post> posts = postRepository.findByUser(user);
        return posts.stream().map(post -> modelMapper.map(post, PostResponseDto.class)).collect(Collectors.toList());
    }
    @Override
    public ApiResponse deletePost(Long postId) throws ResourceNotFoundException {
        Post post=postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(Constants.POST_EXCEPTION_MSG + postId));
        if(post.getMediaUrl()!=null){
            String key=post.getMediaUrl().substring(post.getMediaUrl().lastIndexOf("/")+1);
            if(post.getPostType()==PostType.IMAGE){
                s3Service.deleteImage(key,fileRoot2);
            }
            else{
                s3Service.deleteImage(key,fileRoot3);
            }
        }
        postRepository.delete(post);
        return new ApiResponse("Post deleted for postId "+postId,true, HttpStatus.OK.value());
    }
}
