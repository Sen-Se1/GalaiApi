package com.galai.galai.Controller;

import com.galai.galai.Entity.AuthenticationResponse;
import com.galai.galai.Entity.User;
import com.galai.galai.Service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (@RequestBody User request){
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
