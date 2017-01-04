package com.musingscafe.grabber.core.old;

import java.io.Serializable;

/**
 * Created by ayadav on 11/16/16.
 */
public interface Serializer {
    byte[] serialize(Serializable obj);
    Object deserialize(byte[] objectData);
}
