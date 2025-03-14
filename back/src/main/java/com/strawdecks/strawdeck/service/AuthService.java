package com.strawdecks.strawdeck.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.strawdecks.strawdeck.dao.UsersDao;
import com.strawdecks.strawdeck.modelo.AuthRequest;
import com.strawdecks.strawdeck.modelo.AuthResponse;
import com.strawdecks.strawdeck.modelo.RegisterRequest;
import com.strawdecks.strawdeck.modelo.Users;

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
        // 1. Verificar si el usuario ya existe
        Optional<Users> existingUser = userService.findUserByName(request.getUsername());
        if (existingUser.isPresent()) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre de usuario ya esta en uso");
        }
        Users newUser = new Users();
        newUser.setUsername(request.getUsername());
        newUser.setGmail(request.getGmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setActivo(true);

        userService.createUser(newUser);

        // 3. Generar tokens JWT
        String accessToken = jwtService.generateAccessToken(newUser);
        String refreshToken = jwtService.generateRefreshToken(newUser);

        // 4. Retornar la respuesta con los tokens
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(newUser.getId())
                .build();
    }
}
