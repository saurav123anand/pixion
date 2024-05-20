package com.geeks.pixion.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddDto {
    @NotEmpty
    @Size(min = 4,message = "firstName should have at least 4 characters")
    private String firstName;
    private String lastName;
    @Email(message = "Email is invalid")
    private String email;
    private String password;
    private Date createdTimeStamp;
}
