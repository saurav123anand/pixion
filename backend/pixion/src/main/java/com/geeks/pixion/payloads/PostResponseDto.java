package com.geeks.pixion.payloads;

import com.geeks.pixion.constants.PostType;
import com.geeks.pixion.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String mediaUrL;
    private boolean approved=false;
    private Date postCreationTime;
    private Long totalViews;
    private Long totalLikes;
    private String location;
    private PostType postType;
    private UserResponseDto user;
    private Category category;
}
