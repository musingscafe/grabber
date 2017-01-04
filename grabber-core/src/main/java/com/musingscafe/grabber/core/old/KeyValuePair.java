package com.musingscafe.grabber.core.old;

import com.musingscafe.grabber.core.old.message.GrabberMessage;

/**
 * Created by ayadav on 11/16/16.
 */
public class KeyValuePair {
    private String key;
    private GrabberMessage value;
    private boolean hasValue;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public GrabberMessage getValue() {
        return value;
    }

    public void setValue(GrabberMessage value) {
        this.value = value;
    }

    public boolean isHasValue() {
        return hasValue;
    }

    public void setHasValue(boolean hasValue) {
        this.hasValue = hasValue;
    }
}
