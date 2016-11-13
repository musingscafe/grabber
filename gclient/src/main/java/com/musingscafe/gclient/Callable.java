package com.musingscafe.gclient;

/**
 * Created by ayadav on 11/13/16.
 */
public interface Callable {
    void onTaskCompletion(String identifier, byte[] key, boolean successful);
}
