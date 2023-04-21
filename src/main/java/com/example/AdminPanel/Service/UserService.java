package com.example.AdminPanel.Service;

import com.example.AdminPanel.Entity.UserDto;
import com.example.AdminPanel.Models.Roles;
import com.example.AdminPanel.Models.Users;
import com.example.AdminPanel.Models.UserUuid;
import com.example.AdminPanel.Repository.RoleRepository;
import com.example.AdminPanel.Repository.UserRepository;
import com.example.AdminPanel.Repository.UserRoleRepository;
import com.example.AdminPanel.Repository.UserUuidRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    UserRepository userrepo;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserUuidRepository uuidRepo;
    @Value("${image.path}")
    String imagePath;

    public void addUsers(Users user) {
        userrepo.save(user);
        String role = "USER";
        int userId = userrepo.findByEmail(user.getEmail()).getId();
        int roleId = userRoleRepository.findIdByRoles(role);
        Roles roles = Roles.builder()
                .roleId(roleId)
                .userId(userId)
                .build();
        roleRepository.save(roles);

    }
    public void addUsers(Users user, String role) {
        userrepo.save(user);
        int userId = userrepo.findByEmail(user.getEmail()).getId();
        int roleId = userRoleRepository.findIdByRoles(role);
        Roles roles = Roles.builder()
                .roleId(roleId)
                .userId(userId)
                .build();
        roleRepository.save(roles);

    }

    /*
    public void addUser(UserDto dto) {
        System.out.println("inside adduser");
        users2 user=users2.builder()
                .name(dto.getName())
                .city(dto.getCity())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
        userrepo.save(user);
        System.out.println("inside adduser line 59");
        rolesService.setRoles(userrepo.findByEmail(dto.getEmail()), dto.getRoles());

    }

     */

    public Users getUserByEmail(String Email) {
        System.out.println("userservice " + " 5 " + Email);
        return userrepo.findByEmail(Email);


    }
    public Users updateUserByEmail(String email, UserDto userDto, String fileName) {
        userDto.setUrl(imagePath + fileName);

        userrepo.updateUserByEmail(email, userDto);

        Users users = userrepo.findByEmail(email);

        return users;
    }

    public UserUuid CreateToken(UserUuid uuidEntity){
        System.out.println(uuidEntity);
        if (uuidRepo==null) System.out.println("null");
        // return uuidEntity;

        return uuidRepo.save(uuidEntity);
    }

    public UserUuid getToken(String uuid) {
        return uuidRepo.getUserTokenByUuid(uuid);
    }
    @Transactional
    public int deleteToken(String uuid) {
        return  uuidRepo.deleteByUuid(uuid);
    }


}
