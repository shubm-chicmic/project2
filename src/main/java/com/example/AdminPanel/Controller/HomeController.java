package com.example.AdminPanel.Controller;

import com.example.AdminPanel.Entity.Message;
import com.example.AdminPanel.Models.UserUuid;
import com.example.AdminPanel.Models.Users;
import com.example.AdminPanel.Service.RolesService;
import com.example.AdminPanel.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;


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

    @Message("Visited Home Page")
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
    @Message("Viewed Driver's profile")
    @RequestMapping("driverOverview")
    public String driverOverview() {
        return "driver-overview";
    }




}