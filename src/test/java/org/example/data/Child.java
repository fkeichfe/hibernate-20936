package org.example.data;

import jakarta.persistence.*;

@Entity
public class Child {

    @Id
    @GeneratedValue
    protected Long id;

    public Long getId() {
        return id;
    }

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Parent parent;

    private String name;

    protected Child() {
        // no-args constructor
    }

    public Child(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Child{id=%d, name='%s'}".formatted(id, name);
    }
}
