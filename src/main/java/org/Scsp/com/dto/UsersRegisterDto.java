package org.Scsp.com.dto;

import lombok.Data;
import org.Scsp.com.Enum.AddictionLevel;

@Data
public class UsersRegisterDto {
    private String name;
    private String email;
    private String password;
    private AddictionLevel addictionLevel;
    // Add other fields as needed (e.g., fullName, phone, etc.)
}