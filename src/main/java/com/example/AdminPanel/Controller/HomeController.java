package com.example.AdminPanel.Controller;

import com.example.AdminPanel.Entity.UserDto;
import com.example.AdminPanel.Models.UserUuid;
import com.example.AdminPanel.Models.Users;
import com.example.AdminPanel.Service.RolesService;
import com.example.AdminPanel.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.sql.Driver;
import java.util.Arrays;
import java.util.List;


@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    RolesService rolesService;
    @Autowired
    RestTemplate restTemplate;
    @Value("${mevron.server}")
    String url;
    @RequestMapping("/")
    public String homePage(HttpSession session, Model model) {

        String uuid = (String) session.getAttribute("Authorization");
        System.out.println("\u001B[31m" + "inside homePage  /////////////////" + uuid + "\u001B[0m");
        if(uuid != null) {
            UserUuid userUuid = userService.getToken(uuid);
            Users users = userService.getUserByEmail(userUuid.getEmail());
            model.addAttribute("userName", users.getEmail());
            model.addAttribute("password", users.getPassword());
        }

        return "index";
    }
    @RequestMapping("driverOverview")
    public String driverOverview() {
        return "driver-overview";
    }




}