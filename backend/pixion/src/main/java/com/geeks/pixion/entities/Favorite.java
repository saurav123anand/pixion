package com.geeks.pixion.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "favorites")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@ToString(exclude = {"post", "user"})
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long favoriteId;
    private Date favoriteTime;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
