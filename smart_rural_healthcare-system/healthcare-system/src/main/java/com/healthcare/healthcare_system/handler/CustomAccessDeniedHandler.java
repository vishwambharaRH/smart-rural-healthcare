package com.healthcare.healthcare_system.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        // Log the denial
        System.out.println("Access Denied: " + request.getRequestURI() + " - User: " + request.getUserPrincipal());

        request.setAttribute("errorMessage", "Access Denied! You don't have permission to access this page.");
        request.setAttribute("deniedUrl", request.getRequestURI());
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/error/403");
        dispatcher.forward(request, response);
    }
}
