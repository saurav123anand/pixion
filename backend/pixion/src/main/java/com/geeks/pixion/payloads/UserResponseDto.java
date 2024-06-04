package com.geeks.pixion.payloads;
import com.geeks.pixion.entities.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
//    private String password;
    private Date createdTimeStamp;
    private String profileImageName;
    private Long allTimeRank;
    private Long last30DaysRank;
    private String bio;
    private String instaUrl;
    private String xurl;
    private String portfolioUrl;
    private String linkedinUrl;
    private AddressDto address;
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
