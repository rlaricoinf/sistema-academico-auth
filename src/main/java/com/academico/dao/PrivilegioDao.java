package com.academico.dao;

import com.academico.entity.Privilegio;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * DAO para la gestión de privilegios
 */
@Stateless
public class PrivilegioDao {

    @PersistenceContext(unitName = "AcademicoDS")
    private EntityManager entityManager;

    /**
     * Busca todos los privilegios
     */
    public List<Privilegio> findAll() {
        TypedQuery<Privilegio> query = entityManager.createNamedQuery("Privilegio.findAll", Privilegio.class);
        return query.getResultList();
    }

    /**
     * Busca todos los privilegios activos
     */
    public List<Privilegio> findActivos() {
        TypedQuery<Privilegio> query = entityManager.createNamedQuery("Privilegio.findActivos", Privilegio.class);
        return query.getResultList();
    }

    /**
     * Busca un privilegio por código
     */
    public Optional<Privilegio> findByCodigo(String codigo) {
        TypedQuery<Privilegio> query = entityManager.createNamedQuery("Privilegio.findByCodigo", Privilegio.class);
        query.setParameter("codigo", codigo);
        try {
            return Optional.of(query.getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Busca privilegios por módulo
     */
    public List<Privilegio> findByModulo(String modulo) {
        TypedQuery<Privilegio> query = entityManager.createNamedQuery("Privilegio.findByModulo", Privilegio.class);
        query.setParameter("modulo", modulo);
        return query.getResultList();
    }

    /**
     * Busca un privilegio por ID
     */
    public Optional<Privilegio> findById(Long id) {
        Privilegio privilegio = entityManager.find(Privilegio.class, id);
        return Optional.ofNullable(privilegio);
    }

    /**
     * Guarda un privilegio
     */
    public Privilegio save(Privilegio privilegio) {
        if (privilegio.getId() == null) {
            entityManager.persist(privilegio);
        } else {
            privilegio = entityManager.merge(privilegio);
        }
        return privilegio;
    }

    /**
     * Actualiza un privilegio
     */
    public Privilegio update(Privilegio privilegio) {
        return entityManager.merge(privilegio);
    }

    /**
     * Elimina un privilegio
     */
    public void delete(Long id) {
        Privilegio privilegio = entityManager.find(Privilegio.class, id);
        if (privilegio != null) {
            entityManager.remove(privilegio);
        }
    }

    /**
     * Verifica si existe un privilegio con el código dado
     */
    public boolean existsByCodigo(String codigo) {
        return findByCodigo(codigo).isPresent();
    }
}

