package com.example.AdminPanel.Controller;

import com.example.AdminPanel.Entity.UserDto;
import com.example.AdminPanel.Service.RolesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class ProcessController {
    @Autowired
    RolesService rolesService;
    @Value("${mevron.server}")
    String url;
    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/addRoles")
    @ResponseBody
    public void addRoles(@RequestParam String role) {
        rolesService.addRoles(role);
    }

    //softDelete
    @RequestMapping("/softDelete")
    public String softDelete(HttpServletRequest request) {
        String id = request.getParameter("id");
        String getUsersUrl = url + "/softDeleteUser?id=" + id;
        System.out.println(getUsersUrl);
        Boolean status = restTemplate.getForObject(getUsersUrl, Boolean.class);

        return "redirect:/users";
    }


    //SUSPEND
    @RequestMapping("/suspend")
    public String userSuspend(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        String search = request.getParameter("search");
        String pageNo = request.getParameter("pageNumber");
        String sortBy = request.getParameter("sortBy");
        String order = request.getParameter("order");

        String getUsersUrl = url + "/suspendUser?id=" + id;
        System.out.println(getUsersUrl);
        Boolean status = restTemplate.getForObject(getUsersUrl, Boolean.class);


        return "redirect:/users?search="+search + "&pageNumber=" + pageNo + "&sortBy=" + sortBy + "&order=" + order;
    }
    @RequestMapping("/searchData")
    public String searchUserData(HttpServletRequest request) {
        String search = request.getParameter("search");
        return "redirect:/users?search="+search;
    }
    @RequestMapping("/sortData")
    public String sortData(HttpServletRequest request) {
        String sortBy = request.getParameter("sortBy");
        String order = request.getParameter("order");
        String pageNo = request.getParameter("pageNumber");
        String search = request.getParameter("search");

        order = (order.equals("1") ? "0" : "1");
        return "redirect:/users?sortBy=" + sortBy + "&order=" + order + "&pageNumber=" + pageNo + "&search=" + search;
    }



}
