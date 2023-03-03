package com.example.social_network_gradle.repository.file;

import com.example.social_network_gradle.domain.Entity;
import com.example.social_network_gradle.domain.validators.ValidationException;
import com.example.social_network_gradle.repository.RepositoryException;
import com.example.social_network_gradle.repository.memory.InMemoryRepository;

import java.io.*;
import java.util.Arrays;
import java.util.List;

import com.example.social_network_gradle.domain.validators.Validator;

public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID,E> {
    String fileName;

    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName=fileName;
        loadData();
    }

    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                List<String> attr = Arrays.asList(linie.split(";"));
                E e = extractEntity(attr);
                super.save(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RepositoryException | ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  extract entity  - template method design pattern
     *  creates an entity of type E having a specified list of @code attributes
     * @return an entity of type E
     */

    public abstract E extractEntity(List<String> attributes);
    ///Observatie-Sugestie: in locul metodei template extractEntity, puteti avea un factory pr crearea instantelor entity

    protected abstract String createEntityAsString(E entity);

    @Override
    public E save(E entity) throws RepositoryException, ValidationException {
        E e=super.save(entity);
        if (e==null)
        {
            writeToFile(entity);
        }
        return e;

    }

    @Override
    public void delete(ID id) throws RepositoryException {
        super.delete(id);
        writeAllToFile();
    }

    @Override
    public E update(E entity) throws ValidationException, RepositoryException {
        super.update(entity);
        writeAllToFile();
        return null;
    }

    protected void writeToFile(E entity){
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,true))) {
            bW.write(createEntityAsString(entity));
            bW.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeAllToFile(){
        try (BufferedWriter bW = new BufferedWriter(new FileWriter(fileName,false))) {
            for(E entity : findAll()){
                bW.write(createEntityAsString(entity));
                bW.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

