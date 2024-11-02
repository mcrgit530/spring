package com.example.demo.Config;

import com.example.demo.Model.User;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(this.oidcUserService())
                        )
                        .defaultSuccessUrl("/redirect", true)
                )
                .cors(cors -> cors.configurationSource(request -> {
                    org.springframework.web.cors.CorsConfiguration corsConfig = new org.springframework.web.cors.CorsConfiguration();
                    corsConfig.setAllowedOrigins(List.of("http://localhost:3000"));
                    corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfig.setAllowCredentials(true);
                    corsConfig.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                    return corsConfig;
                }))
                .csrf().disable();

        return http.build();
    }

    private OidcUserService oidcUserService() {
        OidcUserService delegate = new OidcUserService();

        return new OidcUserService() {
            @Override
            public OidcUser loadUser(OidcUserRequest userRequest) {
                OidcUser oidcUser = delegate.loadUser(userRequest);
                String email = oidcUser.getEmail();
                String name = oidcUser.getAttribute("name");
                String photoUrl = oidcUser.getAttribute("picture");
                String GoogleId = oidcUser.getSubject();
                if (!isEmailLoggedIn(email)) {
                    userService.getEmail(email,name,GoogleId,photoUrl);
                }
                System.out.println(email +" " +  name + " " + GoogleId + " " + photoUrl);

                return oidcUser;
            }
        };
    }

    private boolean isEmailLoggedIn(String email) {
        Optional<User> existingUserEmail = userService.findByEmail(email);
        return existingUserEmail.isPresent();
    }
}

