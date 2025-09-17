package com.academico.dao;

import com.academico.entity.Perfil;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la gestión de perfiles
 */
@Stateless
public class PerfilDao {

    @PersistenceContext(unitName = "AcademicoDS")
    private EntityManager entityManager;

    /**
     * Busca todos los perfiles
     */
    public List<Perfil> findAll() {
        TypedQuery<Perfil> query = entityManager.createNamedQuery("Perfil.findAll", Perfil.class);
        return query.getResultList();
    }

    /**
     * Busca todos los perfiles activos
     */
    public List<Perfil> findActivos() {
        TypedQuery<Perfil> query = entityManager.createNamedQuery("Perfil.findActivos", Perfil.class);
        return query.getResultList();
    }

    /**
     * Busca un perfil por nombre
     */
    public Optional<Perfil> findByNombre(String nombre) {
        TypedQuery<Perfil> query = entityManager.createNamedQuery("Perfil.findByNombre", Perfil.class);
        query.setParameter("nombre", nombre);
        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Busca un perfil por ID
     */
    public Optional<Perfil> findById(Long id) {
        Perfil perfil = entityManager.find(Perfil.class, id);
        return Optional.ofNullable(perfil);
    }

    /**
     * Guarda un perfil
     */
    public Perfil save(Perfil perfil) {
        if (perfil.getId() == null) {
            entityManager.persist(perfil);
        } else {
            perfil = entityManager.merge(perfil);
        }
        return perfil;
    }

    /**
     * Actualiza un perfil
     */
    public Perfil update(Perfil perfil) {
        return entityManager.merge(perfil);
    }

    /**
     * Elimina un perfil
     */
    public void delete(Long id) {
        Perfil perfil = entityManager.find(Perfil.class, id);
        if (perfil != null) {
            entityManager.remove(perfil);
        }
    }

    /**
     * Verifica si existe un perfil con el nombre dado
     */
    public boolean existsByNombre(String nombre) {
        return findByNombre(nombre).isPresent();
    }
}

