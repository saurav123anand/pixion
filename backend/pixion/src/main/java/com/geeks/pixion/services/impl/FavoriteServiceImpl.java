package com.geeks.pixion.services.impl;

import com.geeks.pixion.entities.Favorite;
import com.geeks.pixion.entities.Post;
import com.geeks.pixion.entities.User;
import com.geeks.pixion.exceptions.AlreadyExistsException;
import com.geeks.pixion.exceptions.ResourceNotFoundException;
import com.geeks.pixion.payloads.ApiResponse;
import com.geeks.pixion.payloads.FavoriteDto;
import com.geeks.pixion.payloads.FavoriteResponseDto;
import com.geeks.pixion.payloads.PostResponseDto;
import com.geeks.pixion.repositiories.FavoriteRepository;
import com.geeks.pixion.repositiories.PostRepository;
import com.geeks.pixion.repositiories.UserRepository;
import com.geeks.pixion.services.FavoriteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FavoriteResponseDto addFavorite(FavoriteDto favoriteDto) throws ResourceNotFoundException, AlreadyExistsException {
        User user = userRepository.findById(favoriteDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found for userId " + favoriteDto.getUserId()));
        Post post = postRepository.findById(favoriteDto.getPostId()).orElseThrow(() -> new ResourceNotFoundException("Post not found for postId " + favoriteDto.getPostId()));
        if (favoriteRepository.existsByPostAndUser(post,user))
            throw new AlreadyExistsException("Post is already in the user's favorites");
        Favorite favorite=new Favorite();
        favorite.setPost(post);
        favorite.setUser(user);
        favorite.setFavoriteTime(new Date());
        System.out.println("user favorite is "+user.getFavorites());
        Favorite save = favoriteRepository.save(favorite);
        return modelMapper.map(save,FavoriteResponseDto.class);
    }

    @Override
    public ApiResponse removeFavorite(FavoriteDto favoriteDto) throws ResourceNotFoundException {
        User user = userRepository.findById(favoriteDto.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found for userId " + favoriteDto.getUserId()));
        Post post = postRepository.findById(favoriteDto.getPostId()).orElseThrow(() -> new ResourceNotFoundException("Post not found for postId " + favoriteDto.getPostId()));
        Favorite favorite = favoriteRepository.findByPostAndUser(post, user).orElseThrow(() -> new ResourceNotFoundException("Favorite not found!"));
        favoriteRepository.delete(favorite);
        return new ApiResponse("Favorite deleted",true, HttpStatus.OK.value());
    }

    @Override
    public List<FavoriteResponseDto> getFavoritePostsByUser(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for userId " + userId));
        List<Favorite> favorites = favoriteRepository.findAllByUser(user);
        return favorites.stream().map(favorite -> modelMapper.map(favorite,FavoriteResponseDto.class))
                .collect(Collectors.toList());
    }

}
