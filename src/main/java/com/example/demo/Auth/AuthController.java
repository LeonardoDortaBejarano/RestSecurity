package com.example.demo.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

        
	@PostMapping("/login")
	public ResponseEntity<AuthResponse>  login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
	}

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }


    @PostMapping("/test")
    public String getMethodName() {
        return "test";
    }


}


