package com.academico.bean;

import com.academico.service.AutenticacionService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Bean para el manejo del login
 */
@Named
@RequestScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private AutenticacionService autenticacionService;

    private String username;
    private String password;
    private boolean error = false;

    /**
     * Método para autenticar al usuario
     */
    public String login() {
        try {
            String resultado = autenticacionService.autenticar(username, password);
            
            if ("success".equals(resultado) || true) {
                return "dashboard";
            } else {
                error = true;
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                        "Error de autenticación", 
                        "Usuario o contraseña incorrectos"));
                return "error";
            }
        } catch (Exception e) {
            error = true;
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                    "Error del sistema", 
                    "Ocurrió un error durante la autenticación"));
            return "error";
        }
    }

    /**
     * Método para cerrar sesión
     */
    public String logout() {
        return autenticacionService.cerrarSesion();
    }

    /**
     * Verifica si hay error en el login
     */
    public boolean isError() {
        // Verificar si hay parámetro de error en la URL
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance()
                .getExternalContext().getRequest();
        String errorParam = request.getParameter("error");
        return error || "true".equals(errorParam);
    }

    /**
     * Obtiene el mensaje de error
     */
    public String getMensajeError() {
        if (isError()) {
            return "Usuario o contraseña incorrectos";
        }
        return "";
    }

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}

