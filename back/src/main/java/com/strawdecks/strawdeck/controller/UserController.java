package com.strawdecks.strawdeck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.strawdecks.strawdeck.modelo.AuthRequest;
import com.strawdecks.strawdeck.modelo.AuthResponse;
import com.strawdecks.strawdeck.modelo.RegisterRequest;
import com.strawdecks.strawdeck.modelo.Users;
import com.strawdecks.strawdeck.service.AuthService;
import com.strawdecks.strawdeck.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    @PostMapping("/create")
    public ResponseEntity <String> createUser(@RequestBody Users user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok("Usuario Creado con Éxito");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }
    
}
