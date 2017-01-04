package com.musingscafe.grabber.core;

import com.musingscafe.grabber.core.channel.ChannelConfig;
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
    private final GrabberRepository repository;
    private RocksIterator iterator;
    private boolean hasSeekedFirst;

    public RocksDbProducer(Serializer serializer, GrabberRepository repository) {
        this.serializer = serializer;
        this.repository = repository;
    }

    private void seek(String channelIdentifier) {
        if(!hasSeekedFirst){
            this.iterator = repository.readColumn(channelIdentifier);
            iterator.seekToFirst();
            hasSeekedFirst = true;
        }
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

    private GrabberMessage getMessage(RocksIterator iterator){
        byte[] key = iterator.key();
        byte[] value = iterator.value();
        return (GrabberMessage) serializer.deserialize(value);
    }

    @Override
    public List<GrabberMessage> getBatch(ChannelConfig channelConfig) {
        seek(channelConfig.getChannelIdentifier());

        List<GrabberMessage> list = new ArrayList<>();

        if (iterator.isValid()){
            GrabberMessage grabberMessage = getMessage(iterator);
            iterator.next();

            list.add(grabberMessage);
        }

        iterator.close();
        hasSeekedFirst = false;
        return list;
    }
}
