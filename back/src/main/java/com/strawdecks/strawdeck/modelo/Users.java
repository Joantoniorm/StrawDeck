package com.strawdecks.strawdeck.modelo;

import java.util.Collections;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users implements UserDetails{
    private int id;
    private String username;
    private String password;
    private String gmail;
    private Boolean activo;
    // Método obligatorio de UserDetails, devuelve las autoridades del usuario (si tienes roles, puedes añadirlos aquí)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Si decides agregar roles o permisos, puedes hacerlo aquí:
        return Collections.emptyList(); // Si no usas roles, devuelve una lista vacía
    }

    // Método obligatorio de UserDetails, devuelve el nombre de usuario
    @Override
    public String getUsername() {
        return this.username;
    }

    // Método obligatorio de UserDetails, devuelve la contraseña
    @Override
    public String getPassword() {
        return this.password;
    }

    // Método obligatorio de UserDetails, indica si la cuenta no ha expirado
    @Override
    public boolean isAccountNonExpired() {
        return true; // Por ahora retornamos true
    }

    // Método obligatorio de UserDetails, indica si la cuenta no está bloqueada
    @Override
    public boolean isAccountNonLocked() {
        return this.activo; // Retornamos 'activo', que indica si la cuenta está bloqueada o no
    }

    // Método obligatorio de UserDetails, indica si la contraseña no ha expirado
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Por ahora retornamos true
    }

    // Método obligatorio de UserDetails, indica si la cuenta está habilitada
    @Override
    public boolean isEnabled() {
        return this.activo; // Retornamos 'activo', que indica si la cuenta está habilitada
    }
}
