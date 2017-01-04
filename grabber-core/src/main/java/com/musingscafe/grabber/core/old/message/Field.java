package com.musingscafe.grabber.core.old.message;

import java.io.Serializable;

/**
 * Created by ayadav on 11/15/16.
 */
public class Field implements Serializable {
    private final Object value;

    public Field(Object value){
        this.value = value;
    }

    public Object getValue() {
        return value;
    }
}
