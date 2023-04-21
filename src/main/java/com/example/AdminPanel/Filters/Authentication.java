package com.example.AdminPanel.Filters;



import com.example.AdminPanel.Entity.UserDto;
import com.example.AdminPanel.Models.UserUuid;
import com.example.AdminPanel.Models.Users;
import com.example.AdminPanel.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.UUID;


public class Authentication extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;


    public Authentication(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;

        this.userService = userService;

    }

    @Override
    public org.springframework.security.core.Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("Attempt authentication!!");
        String username = request.getParameter("email");
        System.out.println(username+"]]]");
        String password = request.getParameter("password");
        System.out.println(password+"]]]]");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
      return authenticationManager.authenticate(authenticationToken);
      //  return authenticationToken;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, org.springframework.security.core.Authentication authentication) throws IOException, ServletException {
        //User user= (User) authentication.getPrincipal();
        System.out.println("Inside successfulAuthentication ");
        UUID uuid= UUID.randomUUID();
        UserUuid uuidEntity = new UserUuid();
        uuidEntity.setUuid(uuid.toString());
        String username = request.getParameter("email");
        System.out.println("isnide suce " + username);
        uuidEntity.setEmail(username);
//        uuidEntity.setId(1155);
        userService.CreateToken(uuidEntity);

        // userService.saveUser(user1);
        System.out.println("login successfully!!!");

        Users users = userService.getUserByEmail(username);
        String name = users.getFirstName() +"_"+ users.getLastName();
        Cookie cookie1 = new Cookie("UserDetail", name);
        cookie1.setMaxAge(24*60*60);
        Cookie cookie2 = new Cookie("UserImageUrl", users.getImageUrl());
        cookie2.setMaxAge(24*60*60);

        response.addCookie(cookie1);
        response.addCookie(cookie2);

//        users2 users2 = UserService.getUserByEmail(request.getParameter("email"));
//
//        authority.setAuthority(rolesService.findRolesByUserId(users2.getId()));
       //  new ObjectMapper().writeValue(response.getOutputStream(),"Logged in " + uuid.toString());
      //  processController.processDriverPasswordLogin();
         //  new ObjectMapper().writeValue(response.getOutputStream(),uuid.toString());


        request.getSession().setAttribute("Authorization", uuid.toString());
        String redirectUrl = "/dashboard";

        new DefaultRedirectStrategy().sendRedirect(request,response,redirectUrl);
//        new ObjectMapper().writeValue(response.getOutputStream(),"gha");

        System.out.println("FirstFiter " + "1");
        //return "Logged in " + uuid.toString();
    }
}

