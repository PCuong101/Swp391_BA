package org.Scsp.com.dto;

import lombok.Data;

@Data
public class UsersRegisterDto {
    private String name;
    private String email;
    private String password;
    // Add other fields as needed (e.g., fullName, phone, etc.)
}