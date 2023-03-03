package com.example.social_network_gradle.repository.memory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.example.social_network_gradle.domain.validators.ValidationException;
import com.example.social_network_gradle.repository.Repository;
import com.example.social_network_gradle.domain.Entity;
import com.example.social_network_gradle.domain.validators.Validator;
import com.example.social_network_gradle.repository.RepositoryException;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {

    private final Validator<E> validator;
    Map<ID, E> entities;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities = new HashMap<ID, E>();
    }

    @Override
    public E findOne(ID id) throws RepositoryException {
        if (id == null)
            throw new RepositoryException("id must be not null");
        return entities.getOrDefault(id, null);
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) throws ValidationException, RepositoryException {
        if (entity == null)
            throw new RepositoryException("entity must be not null");
        validator.validate(entity);
        if (entities.get(entity.getId()) != null) {
            return entity;
        } else entities.put(entity.getId(), entity);
        return null;
    }

    @Override
    public void delete(ID id) throws RepositoryException {
        if (id == null) {
            throw new RepositoryException("Invalid ID");
        }
        E object = findOne(id);
        if (object == null) throw new RepositoryException("User not found");
        else {
            entities.remove(object.getId());
        }
    }

    @Override
    public E update(E entity) throws ValidationException, RepositoryException {

        if (entity == null)
            throw new RepositoryException("entity must be not null!");
        validator.validate(entity);

        entities.put(entity.getId(), entity);

        if (entities.get(entity.getId()) != null) {
            entities.put(entity.getId(), entity);
            return null;
        }
        return entity;
    }

    @Override
    public void increaseStatus(String a, String b) throws SQLException {

    }
}