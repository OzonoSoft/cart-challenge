package ar.com.ozonosoft.taller.security;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

/**
 * Representa al usuario autenticado dentro del SecurityContext*.
 * No estaba seguro de como recuperar el ID del usuario autenticado,
 * pero lo necesito para validar que el usuario que ejecuta una accion
 * sea el dueño de los datos.
 */
@Getter
public class AuthUser implements UserDetails {

    private final UUID id;
    private final String username;     
    private final String password;  
    private final Collection<? extends GrantedAuthority> authorities;

    public AuthUser(UUID id,
                    String username,
                    String password,
                    Collection<? extends GrantedAuthority> authorities) {
        this.id          = id;
        this.username    = username;
        this.password    = password;
        this.authorities = authorities == null ? Collections.emptyList() : authorities;
    }

    //Metodos que me veo obligado a implementar por UserDetails.
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getPassword()              { return password; }
    @Override public String getUsername()              { return username; }
    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }
}