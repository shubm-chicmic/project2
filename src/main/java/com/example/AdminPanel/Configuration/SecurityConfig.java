package com.example.AdminPanel.Configuration;




import com.example.AdminPanel.Filters.Authentication;
import com.example.AdminPanel.Filters.AuthorizationFilter;
import com.example.AdminPanel.Service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
@Configuration
@EnableWebSecurity
@EnableAspectJAutoProxy
public class SecurityConfig {

    private final UserService userService;

//    private final UserDetailsService userDetailsService;
//
//    public SecurityConfig(UserService userService, UserDetailsService userDetailsService) {
//        this.userService = userService;
//        this.userDetailsService = userDetailsService;
//    }

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }


//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        //Authentication Entry point!!!
//
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
//        return authProvider;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("security " + "1" );

       Authentication filter = new Authentication(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)),userService);
        filter.setFilterProcessesUrl("/login");
        http.csrf().disable();

//        http.authorizeHttpRequests().requestMatchers("/abcd").permitAll();
//        http.authorizeHttpRequests().requestMatchers("/public/rider/register").permitAll();

//
        http.authorizeHttpRequests().anyRequest().permitAll();
        http.addFilter(filter);
        http.addFilterBefore(new AuthorizationFilter(userService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//       // return new BCryptPasswordEncoder(10);
        return NoOpPasswordEncoder.getInstance();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}