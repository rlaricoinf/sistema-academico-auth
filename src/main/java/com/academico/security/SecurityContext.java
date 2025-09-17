package com.academico.security;

import com.academico.entity.Usuario;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * Contexto de seguridad para mantener la información del usuario autenticado
 */
@Named
@SessionScoped
public class SecurityContext implements Serializable {

    private static final long serialVersionUID = 1L;

    private Usuario usuarioAutenticado;
    private boolean autenticado = false;

    /**
     * Establece el usuario autenticado
     */
    public void setUsuarioAutenticado(Usuario usuario) {
        this.usuarioAutenticado = usuario;
        this.autenticado = (usuario != null);
    }

    /**
     * Obtiene el usuario autenticado
     */
    public Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    /**
     * Verifica si hay un usuario autenticado
     */
    public boolean isAutenticado() {
        return autenticado && usuarioAutenticado != null;
    }

    /**
     * Cierra la sesión del usuario
     */
    public void cerrarSesion() {
        this.usuarioAutenticado = null;
        this.autenticado = false;
    }

    /**
     * Verifica si el usuario tiene un perfil específico
     */
    public boolean tienePerfil(String nombrePerfil) {
        if (!isAutenticado()) {
            return false;
        }
        return usuarioAutenticado.getPerfil() != null && 
               usuarioAutenticado.getPerfil().getNombre().equals(nombrePerfil);
    }

    /**
     * Verifica si el usuario tiene un privilegio específico
     */
    public boolean tienePrivilegio(String codigoPrivilegio) {
        if (!isAutenticado()) {
            return false;
        }
        return usuarioAutenticado.getPerfil() != null && 
               usuarioAutenticado.getPerfil().tienePrivilegio(codigoPrivilegio);
    }

    /**
     * Obtiene el nombre completo del usuario
     */
    public String getNombreUsuario() {
        if (isAutenticado()) {
            return usuarioAutenticado.getNombreCompleto();
        }
        return "";
    }

    /**
     * Obtiene el perfil del usuario
     */
    public String getPerfilUsuario() {
        if (isAutenticado() && usuarioAutenticado.getPerfil() != null) {
            return usuarioAutenticado.getPerfil().getNombre();
        }
        return "";
    }
}

