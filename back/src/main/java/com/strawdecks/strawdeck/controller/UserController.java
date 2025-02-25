package com.strawdecks.strawdeck.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.strawdecks.strawdeck.modelo.Users;
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
        userService.createUser(user);
        return ResponseEntity.ok("Usuario Creado con Exito");
    }
    
}
