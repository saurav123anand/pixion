package com.geeks.pixion.entities;

import com.geeks.pixion.constants.PostType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;
    private String title;
    private String content;
    private String mediaUrl;
    private boolean approved=false;
    private Date postCreationTime;
    private Long totalViews;
    private Long totalLikes;
    private String location;
    @Enumerated(EnumType.STRING)
    private PostType postType;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Favorite> favorites;

}
