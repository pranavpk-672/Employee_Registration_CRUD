package com.example.TechNova.config;

import com.example.TechNova.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.ignoringRequestMatchers("/custom_login"))
            .authorizeHttpRequests(authorize -> authorize 
                .requestMatchers("/", "/custom_login", "/register", "/contact", "/about", "/services").permitAll()
                .requestMatchers("/profile", "/edit_profile").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/custom_login")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/home", true)  // Redirect to /home on successful login
                .failureUrl("/custom_login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // URL to trigger logout
                .logoutSuccessUrl("/custom_login?logout=true") // Redirect after logout
                .invalidateHttpSession(true) // Invalidate session
                .deleteCookies("JSESSIONID") // Optionally delete session cookies
            );

        return http.build();
    }
}
