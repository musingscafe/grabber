package com.musingscafe.grabber.core.channel;

import com.musingscafe.grabber.core.*;
import com.musingscafe.grabber.core.connectors.Connector;
import com.musingscafe.grabber.core.consumers.Consumer;

import java.util.List;

/**
 * Created by ayadav on 11/15/16.
 */
public class ChannelExecutionContext {
    private String channelIdentifier;
    private Producer producer;
    private Serializer serializer;
    private GrabberRepository repository;
    private List<Consumer> consumers;
    private Connector connector;

    public ChannelExecutionContext(String channelIdentifier,
                                   GrabberRepository repository,
                                   List<Consumer> consumers,
                                   Connector connector){
        this.channelIdentifier = channelIdentifier;
        this.serializer = newSerializer();
        this.repository = repository;
        this.consumers = consumers;
        this.connector = connector;

        setUpProducer(repository);
    }

    private DefaultSerializer newSerializer() {
        return new DefaultSerializer();
    }

    private void setUpProducer(GrabberRepository repository) {
        producer = new RocksDbProducer(this.serializer, this.channelIdentifier, repository);
    }

    public String getChannelIdentifier() {
        return channelIdentifier;
    }

    public void setChannelIdentifier(String channelIdentifier) {
        this.channelIdentifier = channelIdentifier;
    }

    public GrabberRepository getRepository() {
        return repository;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public Producer getProducer() {
        return producer;
    }

    public Connector getConnector() {
        return connector;
    }

    public Serializer getSerializer() {
        return serializer;
    }
}
