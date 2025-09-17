package com.academico.dao;

import com.academico.entity.Usuario;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la gestión de usuarios
 */
@Stateless
public class UsuarioDao {

    @PersistenceContext(unitName = "AcademicoDS")
    private EntityManager entityManager;

    /**
     * Busca un usuario por su nombre de usuario
     */
    public Optional<Usuario> findByUsername(String username) {
        TypedQuery<Usuario> query = entityManager.createNamedQuery("Usuario.findByUsername", Usuario.class);
        query.setParameter("username", username);
        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Busca un usuario por su email
     */
    public Optional<Usuario> findByEmail(String email) {
        TypedQuery<Usuario> query = entityManager.createNamedQuery("Usuario.findByEmail", Usuario.class);
        query.setParameter("email", email);
        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Busca todos los usuarios activos
     */
    public List<Usuario> findAllActivos() {
        TypedQuery<Usuario> query = entityManager.createNamedQuery("Usuario.findAllActivos", Usuario.class);
        return query.getResultList();
    }

    /**
     * Busca usuarios por perfil
     */
    public List<Usuario> findByPerfil(String nombrePerfil) {
        TypedQuery<Usuario> query = entityManager.createNamedQuery("Usuario.findByPerfil", Usuario.class);
        query.setParameter("perfil", nombrePerfil);
        return query.getResultList();
    }

    /**
     * Guarda un usuario
     */
    public Usuario save(Usuario usuario) {
        if (usuario.getId() == null) {
            entityManager.persist(usuario);
        } else {
            usuario = entityManager.merge(usuario);
        }
        return usuario;
    }

    /**
     * Actualiza un usuario
     */
    public Usuario update(Usuario usuario) {
        return entityManager.merge(usuario);
    }

    /**
     * Elimina un usuario
     */
    public void delete(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        if (usuario != null) {
            entityManager.remove(usuario);
        }
    }

    /**
     * Busca un usuario por ID
     */
    public Optional<Usuario> findById(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        return Optional.ofNullable(usuario);
    }

    /**
     * Verifica si existe un usuario con el username dado
     */
    public boolean existsByUsername(String username) {
        return findByUsername(username).isPresent();
    }

    /**
     * Verifica si existe un usuario con el email dado
     */
    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }

    /**
     * Actualiza el último acceso del usuario
     */
    public void actualizarUltimoAcceso(Long usuarioId) {
        Usuario usuario = entityManager.find(Usuario.class, usuarioId);
        if (usuario != null) {
            usuario.actualizarUltimoAcceso();
            entityManager.merge(usuario);
        }
    }
}

