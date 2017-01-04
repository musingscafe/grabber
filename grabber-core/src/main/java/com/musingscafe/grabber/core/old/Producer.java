package com.musingscafe.grabber.core.old;

import java.io.Closeable;
import java.util.List;

/**
 * Created by ayadav on 11/15/16.
 */
public interface Producer extends Closeable{
    List<KeyValuePair> getBatch();
//    boolean hasNext();
}
