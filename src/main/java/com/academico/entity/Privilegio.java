package com.academico.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entidad que representa un privilegio o permiso en el sistema académico
 */
@Entity
@Table(name = "privilegios")
@NamedQueries({
    @NamedQuery(name = "Privilegio.findAll", query = "SELECT p FROM Privilegio p ORDER BY p.codigo"),
    @NamedQuery(name = "Privilegio.findByCodigo", query = "SELECT p FROM Privilegio p WHERE p.codigo = :codigo"),
    @NamedQuery(name = "Privilegio.findActivos", query = "SELECT p FROM Privilegio p WHERE p.activo = true"),
    @NamedQuery(name = "Privilegio.findByModulo", query = "SELECT p FROM Privilegio p WHERE p.modulo = :modulo")
})
public class Privilegio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "El código del privilegio es obligatorio")
    @Size(max = 50, message = "El código no puede exceder 50 caracteres")
    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @NotBlank(message = "El nombre del privilegio es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Size(max = 200, message = "La descripción no puede exceder 200 caracteres")
    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Size(max = 50, message = "El módulo no puede exceder 50 caracteres")
    @Column(name = "modulo", length = 50)
    private String modulo;

    @Size(max = 100, message = "La URL no puede exceder 100 caracteres")
    @Column(name = "url", length = 100)
    private String url;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @OneToMany(mappedBy = "privilegio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PerfilPrivilegio> perfiles = new ArrayList<>();

    // Constructores
    public Privilegio() {
        this.activo = true;
    }

    public Privilegio(String codigo, String nombre, String descripcion, String modulo, String url) {
        this();
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.modulo = modulo;
        this.url = url;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public List<PerfilPrivilegio> getPerfiles() {
        return perfiles;
    }

    public void setPerfiles(List<PerfilPrivilegio> perfiles) {
        this.perfiles = perfiles;
    }

    // Métodos equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Privilegio that = (Privilegio) o;
        return Objects.equals(id, that.id) && Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigo);
    }

    @Override
    public String toString() {
        return "Privilegio{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", modulo='" + modulo + '\'' +
                ", activo=" + activo +
                '}';
    }
}

