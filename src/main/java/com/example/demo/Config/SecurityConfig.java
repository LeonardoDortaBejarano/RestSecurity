
package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.Jwt.JwtAuthenticationFIlter;

import static org.springframework.security.config.Customizer.withDefaults;;

 @Configuration
 @EnableWebSecurity

public class SecurityConfig {

    final AuthenticationProvider authProvider;
    final JwtAuthenticationFIlter jwtAuthenticationFilter;

    
    public SecurityConfig(AuthenticationProvider authProvider, JwtAuthenticationFIlter jwtAuthenticationFilter){
        this.authProvider = authProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        


        return http
        .csrf(csrf -> 
            csrf
            .disable())//desabilitamos cross site
        .authorizeHttpRequests(authRequest ->
            authRequest
            .requestMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
            )
            .sessionManagement(sessionManager->
                sessionManager 
                  .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
            
    }

    

}

