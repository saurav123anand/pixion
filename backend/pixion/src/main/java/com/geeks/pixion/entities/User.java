package com.geeks.pixion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@ToString(exclude = {"posts", "favorites"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date createdTimeStamp;
    private String profileImageName;
    private Long followers;
    private Long following;
    private Long allTimeRank;
    private Long last30DaysRank;
    private String bio;
    private String instaUrl;
    private String xurl;
    private String portfolioUrl;
    private String linkedinUrl;
    // here address will have foreign key of user
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Address address;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Post> posts;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Favorite> favorites;

}
