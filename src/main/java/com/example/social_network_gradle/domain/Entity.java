package com.example.social_network_gradle.domain;



import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Entity<ID> implements Serializable {
    @Serial
    private static final long serialVersionUID = 7331115341259248461L;
    protected ID id;
    public ID getId() {
        return id;
    }
    public void setId(ID id) {
        this.id = id;
    }
}
