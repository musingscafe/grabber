package com.musingscafe.grabber.core.potential.gonners;

import com.musingscafe.grabber.core.Serializer;
import com.musingscafe.grabber.core.channel.ChannelConfig;
import com.musingscafe.grabber.core.connectors.Connector;
import com.musingscafe.grabber.core.consumers.Consumer;

import java.util.List;

/**
 * Created by ayadav on 11/15/16.
 */
public class ChannelExecutionContext {
    private ChannelConfig channelConfig;
    private Serializer serializer;
    private List<Consumer> consumers;
    private Connector connector;

    public ChannelExecutionContext(ChannelConfig channelConfig,
                                   List<Consumer> consumers,
                                   Connector connector,
                                   Serializer serializer){
        this.channelConfig = channelConfig;
        this.serializer = serializer;
        this.consumers = consumers;
        this.connector = connector;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public Connector getConnector() {
        return connector;
    }

    public Serializer getSerializer() {
        return serializer;
    }

    public ChannelConfig getChannelConfig() {
        return channelConfig;
    }
}
