package com.example.AdminPanel.Controller;

import com.example.AdminPanel.Entity.Data;
import com.example.AdminPanel.Entity.Message;
import com.example.AdminPanel.Service.RolesService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Slf4j

@Controller
public class ProcessController {
    @Autowired
    RolesService rolesService;
    @Value("${mevron.server}")
    String url;
    @Autowired
    RestTemplate restTemplate;

    @Message("Admin Added a Role ")
    @PostMapping("/addRoles")
    @ResponseBody
    public void addRoles(@RequestParam String role) {
        rolesService.addRoles(role);
    }

    //softDelete
    @Message("Admin softDelete a Driver ")

    @RequestMapping("/softDelete")
    public String softDelete(HttpServletRequest request) {
        String id = request.getParameter("id");
        String getUsersUrl = url + "/softDeleteUser?id=" + id;
        System.out.println(getUsersUrl);
        Boolean status = restTemplate.getForObject(getUsersUrl, Boolean.class);
        @Data("id")
        String name = "abcdef";

//        ColorLogger colorLogger = new ColorLogger();
        log.info(name);
        return "redirect:/users";
    }


    //SUSPEND
    @RequestMapping("/suspend")
    @Message("Admin Suspended a User ")

    public String userSuspend(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        String search = request.getParameter("search");
        String pageNo = request.getParameter("pageNumber");
        String sortBy = request.getParameter("sortBy");
        String order = request.getParameter("order");

        String getUsersUrl = url + "/suspendUser";
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getUsersUrl).queryParam("id", id);
        getUsersUrl = builder.buildAndExpand(getUsersUrl).toUri().toString();
        System.out.println(getUsersUrl);
        Boolean status = restTemplate.getForObject(getUsersUrl, Boolean.class);
        getUsersUrl = "/users";
         builder = UriComponentsBuilder.fromUriString(getUsersUrl)
                .queryParam("pageNumber", pageNo)
                 .queryParam("search", search)
                .queryParam("sortBy", sortBy)
                .queryParam("order",order);
        getUsersUrl = builder.buildAndExpand(getUsersUrl).toUri().toString();
        return "redirect:" + getUsersUrl;
        //return "redirect:/users?search="+search + "&pageNumber=" + pageNo + "&sortBy=" + sortBy + "&order=" + order;
    }
    @RequestMapping("/searchData")
    @Message("Admin Search Data ")
    public String searchUserData(HttpServletRequest request) {
        String search = request.getParameter("search");
        return "redirect:/users?search="+search;
    }
    @RequestMapping("/sortData")
    @Message("Admin sorted the user data ")
    public String sortData(HttpServletRequest request) {
        String sortBy = request.getParameter("sortBy");
        String order = request.getParameter("order");
        String pageNo = request.getParameter("pageNumber");
        String search = request.getParameter("search");

        order = (order.equals("1") ? "0" : "1");
        return "redirect:/users?sortBy=" + sortBy + "&order=" + order + "&pageNumber=" + pageNo + "&search=" + search;
    }
    @RequestMapping("/userLogout")
    @Message("Admin Logout ")
    public String userLogout() {

        return "redirect:/";
    }



}
