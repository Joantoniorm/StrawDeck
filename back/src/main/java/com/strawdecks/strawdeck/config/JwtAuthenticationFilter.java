package com.strawdecks.strawdeck.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.strawdecks.strawdeck.modelo.Users;
import com.strawdecks.strawdeck.service.JwtService;
import com.strawdecks.strawdeck.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    
    private final JwtService jwtService;
    
    private final UserService userService;
    
    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    String jwt;
    String username;

    // Si el encabezado Authorization es nulo o no empieza con "Bearer", no hay token
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }

    // Quitamos el "Bearer " y obtenemos el JWT
    jwt = authHeader.substring(7);
    username = jwtService.extractUsername(jwt);

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        // Cargar UserDetails desde el servicio de usuario (ahora tu Users ya es UserDetails)
        UserDetails userDetails = userService.loadUserByUsername(username);

        // Validar el token con UserDetails
        if (jwtService.isTokenValid(jwt, userDetails)) {
            // Crear un token de autenticación con los detalles del usuario y establecerlo en el contexto
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }

    filterChain.doFilter(request, response);
}
}
