package com.musingscafe.gclient;

import com.musingscafe.grabber.GrabberMessage;
import org.apache.commons.lang.SerializationUtils;
import org.rocksdb.*;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by ayadav on 11/12/16.
 */

//TODO: Can we abstract better
class GrabberRepository implements Closeable {
    private final ColumnFamilyDescriptor grabberColumnFamilyDescriptor = new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, new ColumnFamilyOptions());
    private final List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>();
    private final List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<>();
    private final Map<String, ColumnFamilyHandle> columnFamilyHandleMap = new HashMap<>();
    private RocksDB database;
    private DBOptions dbOptions;
    private final List<ChannelConfig> channelConfigs;
    private final String databasePath;
    private ColumnFamilyHandle defaultColumnFamilyHandle;

    public GrabberRepository(String databasePath, List<ChannelConfig> channelConfigs){
        this.channelConfigs = channelConfigs;
        this.databasePath = databasePath;

        setupDBOptions();
        setUpChannels();
        setupDb();
        popDefaults();
        setupHandleMap();
    }

    private void popDefaults() {
        defaultColumnFamilyHandle = columnFamilyHandles.remove(0);
        columnFamilyDescriptors.remove(0);
    }

    public Map<String, ColumnFamilyHandle> getColumnFamilyHandleMap(){
        return columnFamilyHandleMap;
    }

    public void save(String channelIdentifier, GrabberMessage message) {
        try {
            database.put(columnFamilyHandleMap.get(channelIdentifier),
                    new WriteOptions(),
                    UUID.randomUUID().toString().getBytes(),
                    SerializationUtils.serialize(message));

        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    public void remove(String channelIdentifier, byte[] key){
        try {
            database.remove(columnFamilyHandleMap.get(channelIdentifier), key);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    public RocksIterator readColumn(String channelIdentifier){
        RocksIterator iterator = database.newIterator(columnFamilyHandleMap.get(channelIdentifier),
                                            new ReadOptions().setTailing(true).setFillCache(false));

        return iterator;
    }

    private void setupHandleMap() {
        //TODO: no check
        for (int i = 0; i < channelConfigs.size(); i++) {
            columnFamilyHandleMap.put(channelConfigs.get(i).getChannelIdentifier(),
                                                                    columnFamilyHandles.get(i));
        }
    }

    private void setUpChannels(){
        columnFamilyDescriptors.add(grabberColumnFamilyDescriptor);
        for(ChannelConfig channelConfig: channelConfigs){
            columnFamilyDescriptors.add(newColumnFamilyDescriptor(channelConfig.getChannelIdentifier()));
        }
    }

    private void setupDb() {
        try {
            database = RocksDB.open(dbOptions, databasePath, columnFamilyDescriptors, columnFamilyHandles);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    private void setupDBOptions(){
        dbOptions = new DBOptions().setCreateIfMissing(true).setCreateMissingColumnFamilies(true);
    }

    private ColumnFamilyDescriptor newColumnFamilyDescriptor(String descriptorName){
        return new ColumnFamilyDescriptor(descriptorName.getBytes(StandardCharsets.UTF_8), new ColumnFamilyOptions());
    }

    @Override
    public void close() throws IOException {
        if(database != null){
            database.close();
        }

        dbOptions.close();
    }
}
