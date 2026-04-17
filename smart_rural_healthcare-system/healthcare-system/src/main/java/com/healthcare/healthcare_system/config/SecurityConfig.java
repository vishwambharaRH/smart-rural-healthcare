package com.healthcare.healthcare_system.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.healthcare.healthcare_system.handler.AuthenticationSuccessHandlerImpl;
import com.healthcare.healthcare_system.handler.CustomAccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandlerImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/register", "/login", "/css/**", "/js/**", "/images/**", "/h2-console/**", "/error/**").permitAll()
.requestMatchers("/patients").permitAll()
                .requestMatchers("/patients/doctor/add**").hasRole("DOCTOR")
                .requestMatchers("/admin-dashboard").hasRole("ADMIN")
                .requestMatchers("/healthworker-dashboard").hasRole("HEALTHWORKER")
                .requestMatchers("/doctor-dashboard").hasRole("DOCTOR")
                .requestMatchers("/patient-dashboard").hasRole("USER")
                .requestMatchers("/doctors/**", "/appointments/**").authenticated()
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.accessDeniedHandler(new CustomAccessDeniedHandler()))

            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()) // Disable for H2 console
            .headers(headers -> headers.frameOptions().disable()); // H2 console

        return http.build();
    }
}

