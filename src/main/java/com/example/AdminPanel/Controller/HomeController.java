package com.example.AdminPanel.Controller;

import com.example.AdminPanel.Entity.Message;
import com.example.AdminPanel.Models.UserUuid;
import com.example.AdminPanel.Models.Users;
import com.example.AdminPanel.Service.RolesService;
import com.example.AdminPanel.Service.UserActivityService;
import com.example.AdminPanel.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;


@Controller
public class HomeController {
    @Autowired
    UserService userService;
    @Autowired
    UserActivityService userActivityService;
    @Autowired
    RolesService rolesService;
    @Autowired
    RestTemplate restTemplate;
    @Value("${mevron.server}")
    String url;

    @Message("Visited Home Page ")
    @RequestMapping("/")
    public String homePage(HttpSession session, Model model) {

//        String uuid = (String) session.getAttribute("Authorization");
        System.out.println("\u001B[31m" + "inside homePage  /////////////////"  + "\u001B[0m");
        userActivityService.deleteAllActivity();

        return "index";
    }
    @Message("Viewed Driver's profile ")
    @RequestMapping("driverOverview")
    public String driverOverview() {
        return "driver-overview";
    }




}