package com.strawdecks.strawdeck.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.strawdecks.strawdeck.dao.AuthRequest;
import com.strawdecks.strawdeck.dao.AuthResponse;
import com.strawdecks.strawdeck.dao.RegisterRequest;
import com.strawdecks.strawdeck.dao.UsersDao;
import com.strawdecks.strawdeck.modelo.Users;
import com.strawdecks.strawdeck.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    
    public AuthResponse login(AuthRequest request){
        Users user = usersDao.findByName(request.getUsername()).orElseThrow(()->new RuntimeException("Usuario no encontrado"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas");
        }

        //Aqui se dan los tokens para el usuario
        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).userId(user.getId()).build();
    }
    public AuthResponse register ( RegisterRequest request){
        Users newUser = new Users();
        newUser.setUsername(request.getUsername());
        newUser.setGmail(request.getGmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setActivo(true);

        userService.createUser(newUser);
        
        String accessToken = jwtService.generateAccessToken(newUser);
        String refreshToken = jwtService.generateRefreshToken(newUser);
        return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).userId(newUser.getId()).build();
    }
}
