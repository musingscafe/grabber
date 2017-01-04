package com.musingscafe.grabber.core.old;

import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;

/**
 * Created by ayadav on 11/16/16.
 */
public class DefaultSerializer implements Serializer {
    @Override
    public byte[] serialize(Serializable obj) {
        return SerializationUtils.serialize(obj);
    }

    @Override
    public Object deserialize(byte[] objectData) {
        return SerializationUtils.deserialize(objectData);
    }
}
