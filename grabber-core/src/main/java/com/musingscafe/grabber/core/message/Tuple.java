package com.musingscafe.grabber.core.message;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ayadav on 11/15/16.
 */
public class Tuple implements Serializable {

    private final LinkedHashMap<String, Object> fieldMap;
    private final List<String> oderedFieldKeys;

    public Tuple(LinkedHashMap<String, Object> fieldMap) {
        this.fieldMap = fieldMap;
        this.oderedFieldKeys = fieldMap.keySet().stream().collect(Collectors.toList());
    }

    public String getString(int index){
        return (String) getObject(index);
    }

    public <T> T getObject(int index, Class<T> clazz){
        Object object = getObject(index);
        if(object == null){
            return null;
        }

        return clazz.cast(object);
    }

    private Object getObject(int index){
        return fieldMap.get(getKey(index));
    }

    private String getKey(int index){
        return oderedFieldKeys.get(index);
    }
}
