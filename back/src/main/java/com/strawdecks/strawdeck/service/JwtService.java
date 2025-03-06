package com.strawdecks.strawdeck.service;
import com.strawdecks.strawdeck.modelo.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.io.Decoders;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    // Extraer el nombre de usuario del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraer un claim específico del token
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // Extraer todos los claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Generar AccessToken usando UserDetails (User de Spring Security)
    public String generateAccessToken(UserDetails user) {
        return buildToken(user, jwtExpiration);
    }

    // Generar RefreshToken usando UserDetails (User de Spring Security)
    public String generateRefreshToken(UserDetails user) {
        return buildToken(user, refreshExpiration);
    }

    // Construir un token JWT
    private String buildToken(UserDetails user, long expiration) {
        return Jwts.builder()
                .setClaims(Map.of("gmail", user.getUsername())) // Usar username de UserDetails
                .setSubject(user.getUsername())  // Usar username de UserDetails
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public UserDetails convertToUserDetails(Users users) {
    return new User(users.getUsername(), users.getPassword(), new ArrayList<>());
}

    // Verificar si el token es válido
    public boolean isTokenValid(String token, UserDetails user) {
        final String username = extractUsername(token);  // Extrae el username del token
        // Verifica que el nombre de usuario coincida y que el token no haya expirado
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }
    // Verificar si el token ha expirado
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extraer la fecha de expiración
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Obtener la clave de firma para los tokens
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}