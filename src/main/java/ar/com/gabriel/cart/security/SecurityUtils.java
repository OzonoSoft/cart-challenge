package ar.com.gabriel.cart.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/** 
 * Analizando codigo repetido, se propone crear un metodo que centralice la obtencion del usuario actual.
*/
public final class SecurityUtils {

    private SecurityUtils() { }

    public static String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("No hay usuario autenticado");
        }
        return auth.getName();
    }
}