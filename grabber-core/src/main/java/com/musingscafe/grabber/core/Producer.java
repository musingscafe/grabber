package com.musingscafe.grabber.core;

import java.io.Closeable;
import java.io.Serializable;
import java.util.List;

/**
 * Created by ayadav on 11/15/16.
 */
public interface Producer extends Closeable{
    List<KeyValuePair> getBatch();
//    boolean hasNext();
}
