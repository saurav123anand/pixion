package com.geeks.pixion.payloads;
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
    private AddressDto address;
}
