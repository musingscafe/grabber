package com.musingscafe.grabber.core;

import com.musingscafe.grabber.core.message.GrabberMessage;
import org.rocksdb.RocksIterator;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created by ayadav on 11/16/16.
 */
public class RocksDbProducer implements Producer, Closeable{
    private final Serializer serializer;
    private final String channelIdentifier;
    private final GrabberRepository repository;
    private RocksIterator iterator;
    private boolean hasSeekedFirst;

    public RocksDbProducer(Serializer serializer, String channelIdentifier, GrabberRepository repository) {
        this.serializer = serializer;
        this.channelIdentifier = channelIdentifier;
        this.repository = repository;
        this.iterator = repository.readColumn(this.channelIdentifier);
    }

    @Override
    public KeyValuePair next(){
        if(!hasSeekedFirst){
            iterator.seekToFirst();
            hasSeekedFirst = true;
        }

        if (iterator.isValid()){
            KeyValuePair keyValuePair = newKeyValuePair(iterator);
            iterator.next();

            return keyValuePair;
        }

        return nullKeyValuePair();
    }

    public boolean isValid(){
        if(!hasSeekedFirst){
            iterator.seekToFirst();
            hasSeekedFirst = true;
        }
        return iterator.isValid();
    }

    @Override
    public boolean hasNext() {
        return isValid();
    }

    @Override
    public void close() throws IOException {
        iterator.close();
    }

    private KeyValuePair newKeyValuePair(RocksIterator iterator){
        KeyValuePair keyValuePair = new KeyValuePair();

        byte[] key = iterator.key();
        byte[] value = iterator.value();

        keyValuePair.setKey(new String(key, StandardCharsets.UTF_8));
        keyValuePair.setValue((GrabberMessage) serializer.deserialize(value));
        keyValuePair.setHasValue(true);

        return keyValuePair;
    }

    private KeyValuePair nullKeyValuePair(){
        KeyValuePair keyValuePair = new KeyValuePair();
        keyValuePair.setHasValue(false);
        return keyValuePair;
    }
}
