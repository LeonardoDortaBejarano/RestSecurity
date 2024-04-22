package com.example.demo.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.Jwt.JwtService;
import com.example.demo.Person.Person;


@Service
public class AuthService {
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthService(){};


    public AuthResponse register(RegisterRequest registerRequest){
        Person person = new Person(registerRequest.getUsername(),registerRequest.getPassword());
        this.authRepository.save(person);
        AuthResponse authResponse = new AuthResponse(jwtService.getToken(person));
        return authResponse;
    }

    public AuthResponse login(LoginRequest loginRequest){
        
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        UserDetails person =  this.authRepository.findByUserName(loginRequest.getUsername());
        AuthResponse authResponse = new AuthResponse(jwtService.getToken(person));
        
        return authResponse;

    }


}
