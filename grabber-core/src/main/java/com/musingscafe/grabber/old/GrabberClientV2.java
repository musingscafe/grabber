package com.musingscafe.grabber.old;

import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayadav on 1/4/17.
 */
public class GrabberClientV2 {
    private String dbPath;
    private List<String> stores;

    public GrabberClientV2(String dbPath, List<String> stores) {
        Validate.notNull(dbPath);
        Validate.notNull(stores);

        this.dbPath = dbPath;
        this.stores = stores;
    }

    public String getDbPath() {
        return dbPath;
    }

    public List<String> getStores() {
        return stores;
    }
}
