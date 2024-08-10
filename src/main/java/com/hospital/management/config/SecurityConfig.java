package com.hospital.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(jwtUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        resources -> resources.requestMatchers("/assets/**").permitAll())
                .authorizeHttpRequests(home -> home.requestMatchers("/", "/home/**").permitAll())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register/**", "/authenticate/**", "/verify-token/**", "/forgot-password/**")
                        .permitAll())
                .authorizeHttpRequests(logout -> logout.requestMatchers("/logoutProcess/**").permitAll())
                // .authorizeHttpRequests(
                // admin -> admin.requestMatchers("/admin/**").hasRole("ADMIN"))
                // .authorizeHttpRequests(
                // user -> user.requestMatchers("/**").hasAnyRole("ADMIN", "DOCTOR", "USER"))
                // .authorizeHttpRequests(doctor ->
                // doctor.requestMatchers("/doctor/**").hasAnyRole("ADMIN", "DOCTOR"))
                .authorizeHttpRequests(all -> all.anyRequest().permitAll())

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
