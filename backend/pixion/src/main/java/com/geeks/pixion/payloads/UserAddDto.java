package com.geeks.pixion.payloads;

import com.geeks.pixion.entities.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddDto {
    private Long userId;
    private String username;
    @NotEmpty
    @Size(min = 4,message = "firstName should have at least 4 characters")
    private String firstName;
    private String lastName;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,message = "Email is invalid")
    private String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
}
