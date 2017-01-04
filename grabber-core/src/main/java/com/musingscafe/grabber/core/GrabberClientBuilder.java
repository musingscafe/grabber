package com.musingscafe.grabber.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayadav on 1/4/17.
 */
public class GrabberClientBuilder {
    private static final String DEFAULT_DB_PATH = "grabber.db";
    private static final String DEFAULT_STORE = "messageStore";

    private String dbPath;
    private List<String> stores;

    public GrabberClientBuilder withDBPath(String dbPath) {
        this.dbPath = dbPath;
        return this;
    }

    public GrabberClientBuilder withStores(List<String> storeNames) {
        this.stores = storeNames;
        return this;
    }

    public GrabberClientV2 build() {
        dbPath = getDbPath();
        stores = getStores();
        return new GrabberClientV2(dbPath, stores);
    }

    private List<String> getDefaultStores() {
        List<String> defaultStores = new ArrayList<>();
        defaultStores.add(DEFAULT_STORE);

        return defaultStores;
    }

    private String getDbPath() {
        return dbPath == null ? DEFAULT_DB_PATH : dbPath;
    }

    private List<String> getStores() {
        return stores == null || stores.size() == 0 ? getDefaultStores() : stores;
    }
}
