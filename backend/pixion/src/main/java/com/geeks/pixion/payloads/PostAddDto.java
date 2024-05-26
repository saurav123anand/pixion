package com.geeks.pixion.payloads;

import com.geeks.pixion.constants.PostType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostAddDto {
    private String title;
    private String content;
    private Date postCreationTime;
    private String location;
    private UserAddDto user;
    private CategoryAddDto category;
    @Enumerated(EnumType.STRING)
    private PostType postType;
}
