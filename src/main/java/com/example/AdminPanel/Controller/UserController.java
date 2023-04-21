package com.example.AdminPanel.Controller;


import com.example.AdminPanel.Entity.UserDto;
import com.example.AdminPanel.Models.UserUuid;
import com.example.AdminPanel.Models.Users;
import com.example.AdminPanel.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RestTemplate restTemplate;
    @Value("${mevron.server}")
    String url;
    @Value("${image.path2}")
    String imagePath;

    @RequestMapping("/dashboard")

    public String adminDashboard(HttpSession session, Model model) {

        System.out.println("\u001B[33m" + "adminDashboard " + "\u001B[0m");

        return "dashboard";
    }

    @RequestMapping("/profile")
    public String profile(Model model, Principal principal) {
        Users users = userService.getUserByEmail(principal.getName());
        model.addAttribute("User", users);
        return "user-profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(
            @RequestParam("imageUrl") MultipartFile multipartFile,
            @ModelAttribute("userDto") UserDto userDto, Model model, HttpServletResponse response

    ) throws IOException {


        String folder = imagePath;
        System.out.println(imagePath);
        byte[] bytes = multipartFile.getBytes();
        Path path = Paths.get(folder +  multipartFile.getOriginalFilename());
        System.out.println("\u001B[33m" + path + "\u001B[0m" );
        Files.write(path, bytes);

        System.out.println("\u001B[33m" + "updateProfile =  " + userDto + "\u001B[0m");
        String fileName = multipartFile.getOriginalFilename();
        Users users = userService.updateUserByEmail(userDto.getEmail(), userDto, fileName);
        System.out.println("\u001B[33m" + "updateProfile =  " + users + "\u001B[0m");

        String name = users.getFirstName() +"_"+ users.getLastName();
        Cookie cookie1 = new Cookie("UserDetail", name);
        cookie1.setMaxAge(24*60*60);
        Cookie cookie2 = new Cookie("UserImageUrl", users.getImageUrl());
        cookie2.setMaxAge(24*60*60);

        response.addCookie(cookie1);
        response.addCookie(cookie2);

        model.addAttribute("User", users);
        return "user-profile";
    }
    @RequestMapping("/users")
    public String Users(Model model, HttpServletRequest request) {
        System.out.println("inside Users ");
        String pageNumber = request.getParameter("pageNumber");
        String pageSize = request.getParameter("pageSize");
        String target = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        String order = request.getParameter("order");
        if(pageSize == null) pageSize = "6";

        if(pageNumber == null) pageNumber = "0";
        if(sortBy == null) sortBy = "firstName";
        if(order ==  null) order = "1";



        System.out.println("\u001B[33m" + pageNumber + " " + pageSize + "\u001B[0m");
        String getUsersUrl = url + "/getAllUsers?pageNumber=" + pageNumber + "&pageSize=" + pageSize + "&search=" + target + "&sortBy=" + sortBy + "&order=" + order;
        Object[] drivers = restTemplate.getForObject(getUsersUrl, Object[].class);
        List<Object> driversList = Arrays.asList(drivers);

        System.out.println(driversList);
        //Model

        int size = 10;
        int[] totalPages = new int[size];
        System.out.println("\u001B[33m" +pageSize + " " +  totalPages.length + "\u001B[0m");
        model.addAttribute("Drivers",driversList);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("maxPage", size);

        model.addAttribute("searchValue" , target);
        model.addAttribute("order", order);
        System.out.println("Page Number   =   " + model.getAttribute("currentPage"));
        System.out.println("target = " + target);
        System.out.println("Search   =   " + model.getAttribute("searchValue"));

        return "users";
    }
    //diver rider update
    @RequestMapping("/userProfile")
    public String userProfile(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        System.out.println("id = " + id);
        String getUsersUrl = url + "/getUser?id=" + id;
        System.out.println(getUsersUrl);
        UserDto user = restTemplate.getForObject(getUsersUrl, UserDto.class);
        System.out.println(user);

        model.addAttribute("User", user);
        return "all-user-profile";
    }

    @RequestMapping("/updateUserProfile")
    public String updateUserProfile(UserDto userDto) {


        String getUsersUrl = url + "/updateUsers";
//         create request body
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", "123");
        jsonObject.put("password", "1234");

// set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);

// send request and parse result
        ResponseEntity<String> loginResponse = restTemplate
                .exchange(getUsersUrl, HttpMethod.POST, entity, String.class);
        if (loginResponse.getStatusCode() == HttpStatus.OK) {
            // JSONObject userJson = new JSONObject(loginResponse.getBody());
        } else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            // nono... bad credentials
        }
        return "all-user-profile";
    }

    //Add driver
    @RequestMapping("addDriver")
    public String addDriver(){
        return "add-driver";
    }
    @RequestMapping("/addDriverByAdmin")
    public String addDriverByAdmin(UserDto userDto) {
        String getUsersUrl = url + "/processDriverRegister";
//         create request body
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("fname", userDto.getFirstName());
//        jsonObject.put("lname", userDto.getLastName());
//        jsonObject.put("phone", userDto.getPhone());
//        jsonObject.put("email", userDto.getEmail());
//        jsonObject.put("city", userDto.getCity());
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getUsersUrl)
                // Add query parameter
                .queryParam("fname", userDto.getFirstName())
                .queryParam("lname", userDto.getLastName())
                .queryParam("phone", userDto.getPhone())
                .queryParam("email", userDto.getEmail())
                .queryParam("city", userDto.getCity());

        System.out.println("\u001B[33m" + builder.buildAndExpand(getUsersUrl).toUri() + "\u001B[0m");
        getUsersUrl = builder.buildAndExpand(getUsersUrl).toUri().toString();
        String response = restTemplate.getForObject(getUsersUrl, String.class);

// set headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<String>( headers);
//
//// send request and parse result
//        ResponseEntity<String> loginResponse = restTemplate
//                .exchange(getUsersUrl, HttpMethod.POST, entity, String.class);
//        if (loginResponse.getStatusCode() == HttpStatus.OK) {
//            // JSONObject userJson = new JSONObject(loginResponse.getBody());
//        } else if (loginResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
//            // nono... bad credentials
//        }
        return "add-driver";
    }

}