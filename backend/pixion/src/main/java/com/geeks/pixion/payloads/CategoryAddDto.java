package com.geeks.pixion.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryAddDto {
    private Long categoryId;
    @NotEmpty
    @Size(min = 4,message = "title should have at least 4 characters")
    private String title;
    @NotEmpty
    @Size(min = 5,message = "description should have at least 5 characters")
    private String description;
}
