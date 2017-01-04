package com.musingscafe.grabber.core.old;

import com.musingscafe.grabber.core.old.message.GrabberMessage;
import org.rocksdb.RocksIterator;
import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
    }

    private void seek() {
        if(!hasSeekedFirst){
            this.iterator = repository.readColumn(this.channelIdentifier);
            iterator.seekToFirst();
            hasSeekedFirst = true;
        }
    }

    public boolean isValid(){
        seek();

        return iterator.isValid();
    }

    @Override
    public void close() throws IOException {
        if(iterator != null){
            iterator.close();
        }
    }

    private KeyValuePair newKeyValuePair(RocksIterator iterator){
        KeyValuePair keyValuePair = new KeyValuePair();

        byte[] key = iterator.key();
        byte[] value = iterator.value();

        keyValuePair.setKey(new String(key, StandardCharsets.ISO_8859_1));
        keyValuePair.setValue((GrabberMessage) serializer.deserialize(value));
        keyValuePair.setHasValue(true);

        return keyValuePair;
    }

    private KeyValuePair nullKeyValuePair(){
        KeyValuePair keyValuePair = new KeyValuePair();
        keyValuePair.setHasValue(false);
        return keyValuePair;
    }

    @Override
    public List<KeyValuePair> getBatch() {
        seek();

        List<KeyValuePair> list = new ArrayList<>();

        if (iterator.isValid()){
            KeyValuePair keyValuePair = newKeyValuePair(iterator);
            iterator.next();

            list.add(keyValuePair);
        }

        iterator.close();
        hasSeekedFirst = false;
        return list;
    }
}
