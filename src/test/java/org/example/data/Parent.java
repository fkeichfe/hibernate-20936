package org.example.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.SQLRestriction;

@Entity
@SQLRestriction("family = 6")
public class Parent {

    @Id
    @GeneratedValue
    private Long id;

    private int family;

    private String name;

    protected Parent() {
        // no-args constructor
    }

    public Parent(final String name) {
        this.name = name;
        this.family = 6;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "Parent{id=%d, name='%s'}"
            .formatted(
                id,
                name
            );
    }
}
