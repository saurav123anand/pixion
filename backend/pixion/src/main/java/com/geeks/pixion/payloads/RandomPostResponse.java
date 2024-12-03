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
public class RandomPostResponse {
    private Long postId;
    private String mediaUrL;
    private String author;
}
