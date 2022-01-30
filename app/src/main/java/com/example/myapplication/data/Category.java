package com.example.myapplication.data;

import java.io.Serializable;

public class Category implements Serializable {
    private final int id;
    private final String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (o == null || o.getClass() != this.getClass())
            return false;

        Category category = (Category) o;
        return category.getId() == this.getId()
                || category.getName().equals(this.getName());
    }
}