package com.musingscafe.grabber.core;

import java.io.Serializable;

/**
 * Created by ayadav on 11/15/16.
 */
public interface Producer {
    KeyValuePair next();
    boolean hasNext();
}
