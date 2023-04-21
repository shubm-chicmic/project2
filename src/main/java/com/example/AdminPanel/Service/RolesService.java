package com.example.AdminPanel.Service;


import com.example.AdminPanel.Models.UsersRoles;
import com.example.AdminPanel.Repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolesService {
    @Autowired
    UserRoleRepository userRoleRepository;

    public void addRoles(String role) {
        UsersRoles usersRoles = UsersRoles.builder()
                .Roles(role).build();

        userRoleRepository.save(usersRoles);
    }
}
