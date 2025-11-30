package com.homsim.jeld.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.function.Consumer;

/**
 * Base class for all data entity services that are using Hibernate to persist data.
 *
 * @param <T> Class of the entity
 */
public abstract class DatabaseBaseService<T> {
    protected final EntityManagerFactory emf;
    private final Class<T> entityClass;

    public DatabaseBaseService(EntityManagerFactory emf, Class<T> entityClass) {
        this.emf = emf;
        this.entityClass = entityClass;
    }

    protected void inTransaction(Consumer<EntityManager> work) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            work.accept(em);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void save(T entity) {
        inTransaction(em -> em.persist(entity));
    }

    public T findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(entityClass, id);
        } finally {
            em.close();
        }
    }

    public List<T> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("from " + entityClass.getSimpleName(), entityClass)
                    .getResultList();
        }
    }

    /* not sure if this will work as intended */
    public void update(T entity) {
        inTransaction(em -> em.merge(entity));
    }

    public void delete(T entity) {
        inTransaction(em -> em.remove(em.merge(entity)));
    }
}