package com.musingscafe.grabber.core.channel;

import com.musingscafe.grabber.core.GrabberRepository;
import com.musingscafe.grabber.core.Producer;
import com.musingscafe.grabber.core.connectors.Connector;
import com.musingscafe.grabber.core.consumers.Consumer;

import java.util.List;

/**
 * Created by ayadav on 11/15/16.
 */
public class ChannelConfig {
    private final String channelIdentifier;
    private final List<Consumer> consumers;
    private final Connector connector;
    private GrabberRepository grabberRepository;
    private Producer producer;
    private boolean shouldExecuteSelf;

    public ChannelConfig(String channelIdentifier,
                         List<Consumer> consumers,
                         Connector connector,
                         boolean shouldExecuteSelf) {
        this.channelIdentifier = channelIdentifier;
        this.consumers = consumers;
        this.connector = connector;
        this.shouldExecuteSelf = shouldExecuteSelf;
    }

    public String getChannelIdentifier() {
        return channelIdentifier;
    }

    public List<Consumer> getConsumers() {
        //TODO: copy and give
        return consumers;
    }

    public Connector getConnector() {
        return connector;
    }

    public GrabberRepository getGrabberRepository() {
        return grabberRepository;
    }

    public void setGrabberRepository(GrabberRepository grabberRepository) {
        this.grabberRepository = grabberRepository;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public boolean isShouldExecuteSelf() {
        return shouldExecuteSelf;
    }
}
