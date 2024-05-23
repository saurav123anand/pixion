package com.geeks.pixion.payloads;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String profileImageName;
    private String bio;
    private String instaUrl;
    private String xurl;
    private String portfolioUrl;
    private String linkedinUrl;
    private AddressDto address;
}
