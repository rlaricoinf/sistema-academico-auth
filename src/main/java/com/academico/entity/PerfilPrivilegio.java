package com.academico.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entidad de asociación entre Perfil y Privilegio
 */
@Entity
@Table(name = "perfil_privilegios")
@IdClass(PerfilPrivilegioId.class)
public class PerfilPrivilegio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "privilegio_id", nullable = false)
    private Privilegio privilegio;

    // Constructores
    public PerfilPrivilegio() {
    }

    public PerfilPrivilegio(Perfil perfil, Privilegio privilegio) {
        this.perfil = perfil;
        this.privilegio = privilegio;
    }

    // Getters y Setters
    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Privilegio getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(Privilegio privilegio) {
        this.privilegio = privilegio;
    }

    // Métodos equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerfilPrivilegio that = (PerfilPrivilegio) o;
        return Objects.equals(perfil, that.perfil) && Objects.equals(privilegio, that.privilegio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(perfil, privilegio);
    }

    @Override
    public String toString() {
        return "PerfilPrivilegio{" +
                "perfil=" + (perfil != null ? perfil.getNombre() : null) +
                ", privilegio=" + (privilegio != null ? privilegio.getCodigo() : null) +
                '}';
    }
}

