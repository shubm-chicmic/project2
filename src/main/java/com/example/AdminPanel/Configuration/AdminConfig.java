package com.example.AdminPanel.Configuration;

import com.example.AdminPanel.Models.Users;
import com.example.AdminPanel.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.CookieValue;

@Configuration
@PropertySource("classpath:application.properties")
public class AdminConfig {

    @Autowired
    UserService userService;
    @Bean
    public void createAdmin() {
        String email = "shubham@gmail.com";

       Users users = userService.getUserByEmail(email);
        if(users == null) {
            String password = "1234";
            String role = "ADMIN";
            Users users1 = Users.builder()
                    .firstName("Shubham")
                    .lastName("Mishra")
                    .email(email)
                    .password(password)
                    .city("Mohali")
                    .phoneNo("9876543210")
                    .build();
            userService.addUsers(users1, role);
        }
    }




}
