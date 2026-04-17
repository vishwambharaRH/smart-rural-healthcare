package com.healthcare.healthcare_system.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.healthcare.healthcare_system.config.CustomUserDetails;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

@Override
    public void onAuthenticationSuccess(HttpServletRequest request, 
        HttpServletResponse response, Authentication authentication) 
        throws IOException, ServletException {
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String roleName = userDetails.getUser().getRole().name();
        
        String redirectUrl = switch (roleName) {
            case "USER" -> "/patient-dashboard";
            case "DOCTOR" -> "/doctor-dashboard";
            case "HEALTHWORKER" -> "/healthworker-dashboard";
            case "ADMIN" -> "/admin-dashboard";
            default -> "/";
        };

        response.sendRedirect(redirectUrl);
    }
}

