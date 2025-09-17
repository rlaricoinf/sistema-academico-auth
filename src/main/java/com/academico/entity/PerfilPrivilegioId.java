package com.academico.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase de clave compuesta para la entidad PerfilPrivilegio
 */
public class PerfilPrivilegioId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long perfil;
    private Long privilegio;

    // Constructores
    public PerfilPrivilegioId() {
    }

    public PerfilPrivilegioId(Long perfil, Long privilegio) {
        this.perfil = perfil;
        this.privilegio = privilegio;
    }

    // Getters y Setters
    public Long getPerfil() {
        return perfil;
    }

    public void setPerfil(Long perfil) {
        this.perfil = perfil;
    }

    public Long getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(Long privilegio) {
        this.privilegio = privilegio;
    }

    // Métodos equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerfilPrivilegioId that = (PerfilPrivilegioId) o;
        return Objects.equals(perfil, that.perfil) && Objects.equals(privilegio, that.privilegio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(perfil, privilegio);
    }

    @Override
    public String toString() {
        return "PerfilPrivilegioId{" +
                "perfil=" + perfil +
                ", privilegio=" + privilegio +
                '}';
    }
}

