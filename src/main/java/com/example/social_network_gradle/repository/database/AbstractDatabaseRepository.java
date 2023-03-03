package com.example.social_network_gradle.repository.database;

import com.example.social_network_gradle.domain.Entity;
import com.example.social_network_gradle.domain.user.User;
import com.example.social_network_gradle.domain.validators.ValidationException;
import com.example.social_network_gradle.domain.validators.Validator;
import com.example.social_network_gradle.repository.Repository;
import com.example.social_network_gradle.repository.RepositoryException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDatabaseRepository <ID, E extends Entity<ID>> implements Repository<ID, E> {

    private final Validator<E> validator;
    protected final Connection connection;

    protected AbstractDatabaseRepository(Validator<E> validator, Connection connection) {
        this.validator = validator;
        this.connection = connection;
    }

    @Override
    public E findOne(ID id) throws RepositoryException {
        try {
            return getEntity(id);
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    protected abstract void insertEntity(E entity) throws SQLException;
    @Override
    public E save(E entity) throws RepositoryException, ValidationException{
        try {
            validator.validate(entity);
            E variable = getEntity(entity.getId());
            if(variable != null) return variable;
            insertEntity(entity);
        } catch (SQLException exception) {
            throw new RepositoryException(exception.getMessage());
        }
        return null;
    }

    protected abstract E getEntity(ID id) throws SQLException, RepositoryException;

    protected abstract void deleteEntity(ID id) throws SQLException;
    @Override
    public void delete(ID id) throws RepositoryException {
        try {
            E entity = getEntity(id);
            if(entity == null) throw new RepositoryException("Not found");
            deleteEntity(id);
        } catch (SQLException exception) {
            throw new RepositoryException(exception.getMessage());
        }
    }

    @Override
    public E update(E entity) throws RepositoryException, ValidationException {
        return null;
    }
}
