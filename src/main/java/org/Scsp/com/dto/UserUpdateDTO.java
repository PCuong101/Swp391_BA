// src/main/java/org/Scsp/com/dto/UserUpdateDTO.java
package org.Scsp.com.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    // Chỉ cho phép cập nhật những trường này
    private String name;
    private String email;
    private String profilePicture;
    private String password;// Frontend sẽ gửi URL ảnh ở đây
}