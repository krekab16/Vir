package com.example.Beadando;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;

@Component
@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException{
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER_JPEG")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER_PNG")) ||
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER_GIF"))){

            response.sendRedirect("/user");
        }
        else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            response.sendRedirect("/admin");
        }
        else {
            response.sendRedirect("/defaultPage");
        }
        log.info("Bejelentkezés sikeres. Szerepkör: {}", authentication.getAuthorities());

    }
}