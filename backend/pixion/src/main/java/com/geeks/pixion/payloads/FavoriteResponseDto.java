package com.geeks.pixion.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResponseDto {
    private Long favoriteId;
    private Date favoriteTime;
    private PostResponseDto post;
    private UserResponseDto user;

}
