package com.academico.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entidad que representa un perfil de usuario en el sistema académico
 */
@Entity
@Table(name = "perfiles")
@NamedQueries({
    @NamedQuery(name = "Perfil.findAll", query = "SELECT p FROM Perfil p ORDER BY p.nombre"),
    @NamedQuery(name = "Perfil.findByNombre", query = "SELECT p FROM Perfil p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Perfil.findActivos", query = "SELECT p FROM Perfil p WHERE p.activo = true")
})
public class Perfil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "El nombre del perfil es obligatorio")
    @Size(max = 50, message = "El nombre del perfil no puede exceder 50 caracteres")
    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;

    @Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PerfilPrivilegio> privilegios = new ArrayList<>();

    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Usuario> usuarios = new ArrayList<>();

    // Constructores
    public Perfil() {
        this.activo = true;
    }

    public Perfil(String nombre, String descripcion) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<PerfilPrivilegio> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(List<PerfilPrivilegio> privilegios) {
        this.privilegios = privilegios;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    // Métodos de utilidad
    public void agregarPrivilegio(Privilegio privilegio) {
        PerfilPrivilegio perfilPrivilegio = new PerfilPrivilegio(this, privilegio);
        this.privilegios.add(perfilPrivilegio);
        privilegio.getPerfiles().add(perfilPrivilegio);
    }

    public void removerPrivilegio(Privilegio privilegio) {
        PerfilPrivilegio perfilPrivilegio = new PerfilPrivilegio(this, privilegio);
        privilegio.getPerfiles().remove(perfilPrivilegio);
        this.privilegios.remove(perfilPrivilegio);
    }

    public boolean tienePrivilegio(String codigoPrivilegio) {
        return privilegios.stream()
                .anyMatch(pp -> pp.getPrivilegio().getCodigo().equals(codigoPrivilegio));
    }

    // Métodos equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perfil perfil = (Perfil) o;
        return Objects.equals(id, perfil.id) && Objects.equals(nombre, perfil.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return "Perfil{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", activo=" + activo +
                '}';
    }
}

