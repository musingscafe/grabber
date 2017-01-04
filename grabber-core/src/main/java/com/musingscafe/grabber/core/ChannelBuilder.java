package com.musingscafe.grabber.core;

import com.musingscafe.grabber.core.connectors.Connector;
import com.musingscafe.grabber.core.consumers.Consumer;

import java.util.List;

/**
 * Created by ayadav on 11/15/16.
 */
public class ChannelBuilder {
    private Channel channel = new Channel();

    public ChannelBuilder setChannelIdentifier(String channelIdentifier){
        channel.setChannelIdentifier(channelIdentifier);
        return this;
    }

    public ChannelBuilder setConsumers(List<Consumer> consumers){
        channel.setConsumers(consumers);
        return this;
    }

    public ChannelBuilder setConnector(Connector connector){
        channel.setConnector(connector);
        return this;
    }

    public Channel build(){
        return channel;
    }

}
