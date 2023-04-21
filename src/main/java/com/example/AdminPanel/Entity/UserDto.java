package com.example.AdminPanel.Entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserDto {
    int id;
    String firstName;
    String lastName;
    String phone;
    String email;
    String url = "";
    String city;
    String password;
    String role;
    Boolean isSuspend;
    Boolean isDelete;

}
