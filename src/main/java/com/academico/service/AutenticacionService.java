package com.academico.service;

import com.academico.entity.Usuario;
import com.academico.entity.Privilegio;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la autenticación y autorización
 */
@Named
@SessionScoped
public class AutenticacionService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioService usuarioService;

    private Usuario usuarioLogueado;
    private List<Privilegio> privilegiosUsuario;

    /**
     * Autentica un usuario
     */
    public String autenticar(String username, String password) {
        Optional<Usuario> usuarioOpt = usuarioService.autenticar(username, password);
        
        if (usuarioOpt.isPresent()) {
            usuarioLogueado = usuarioOpt.get();
            cargarPrivilegiosUsuario();
            return "success";
        } else {
            return "error";
        }
    }

    /**
     * Cierra la sesión del usuario
     */
    public String cerrarSesion() {
        usuarioLogueado = null;
        privilegiosUsuario = null;
        
        // Invalidar la sesión
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        return "logout";
    }

    /**
     * Verifica si el usuario está autenticado
     */
    public boolean isAutenticado() {
        return usuarioLogueado != null;
    }

    /**
     * Verifica si el usuario tiene un privilegio específico
     */
    public boolean tienePrivilegio(String codigoPrivilegio) {
        if (!isAutenticado()) {
            return false;
        }
        return usuarioService.tienePrivilegio(usuarioLogueado, codigoPrivilegio);
    }

    /**
     * Verifica si el usuario tiene un perfil específico
     */
    public boolean tienePerfil(String nombrePerfil) {
        if (!isAutenticado()) {
            return false;
        }
        return usuarioLogueado.getPerfil() != null && 
               usuarioLogueado.getPerfil().getNombre().equals(nombrePerfil);
    }

    /**
     * Verifica si el usuario es director
     */
    public boolean esDirector() {
        return tienePerfil("DIRECTOR");
    }

    /**
     * Verifica si el usuario es profesor
     */
    public boolean esProfesor() {
        return tienePerfil("PROFESOR");
    }

    /**
     * Verifica si el usuario es estudiante
     */
    public boolean esEstudiante() {
        return tienePerfil("ESTUDIANTE");
    }

    /**
     * Carga los privilegios del usuario logueado
     */
    private void cargarPrivilegiosUsuario() {
        if (usuarioLogueado != null && usuarioLogueado.getPerfil() != null) {
            privilegiosUsuario = usuarioLogueado.getPerfil().getPrivilegios()
                    .stream()
                    .map(pp -> pp.getPrivilegio())
                    .filter(p -> p.getActivo())
                    .toList();
        }
    }

    /**
     * Obtiene el nombre completo del usuario logueado
     */
    public String getNombreUsuario() {
        if (isAutenticado()) {
            return usuarioLogueado.getNombreCompleto();
        }
        return "";
    }

    /**
     * Obtiene el perfil del usuario logueado
     */
    public String getPerfilUsuario() {
        if (isAutenticado() && usuarioLogueado.getPerfil() != null) {
            return usuarioLogueado.getPerfil().getNombre();
        }
        return "";
    }

    // Getters y Setters
    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public List<Privilegio> getPrivilegiosUsuario() {
        return privilegiosUsuario;
    }

    public void setPrivilegiosUsuario(List<Privilegio> privilegiosUsuario) {
        this.privilegiosUsuario = privilegiosUsuario;
    }
}

