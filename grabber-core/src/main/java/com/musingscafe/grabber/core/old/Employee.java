package com.musingscafe.grabber.core.old;

import java.io.Serializable;

/**
 * Created by ayadav on 11/15/16.
 */
public class Employee implements Serializable{
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
