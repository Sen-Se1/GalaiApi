package com.galai.galai.Controller;

import com.galai.galai.Entity.AuthenticationResponse;
import com.galai.galai.Entity.User;
import com.galai.galai.Service.AuthenticationService;
import com.galai.galai.Service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final JwtService jwtService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login (@RequestBody User request){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.ok(false);
        }

        String jwtToken = token.replace("Bearer ", "");
        boolean isValid = jwtService.isTokenValid(jwtToken);
        return ResponseEntity.ok(isValid);
    }

}
