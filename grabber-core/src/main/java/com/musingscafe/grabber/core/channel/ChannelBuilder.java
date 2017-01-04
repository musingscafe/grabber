package com.musingscafe.grabber.core.channel;

import com.musingscafe.grabber.core.connectors.Connector;
import com.musingscafe.grabber.core.consumers.Consumer;
import com.musingscafe.grabber.core.registery.ObjectFactory;
import com.musingscafe.grabber.core.registery.ServiceLocator;
import com.musingscafe.grabber.core.registery.ServiceRegistry;

import java.util.List;

/**
 * Created by ayadav on 11/15/16.
 */
public class ChannelBuilder {
    private List<Consumer> consumers;
    private Connector connector;
    private String channelIndentifier;
    private boolean shouldExecuteSelf;

    public ChannelBuilder setChannelIdentifier(String channelIdentifier){
        this.channelIndentifier = channelIdentifier;
        return this;
    }

    public ChannelBuilder setConsumers(List<Consumer> consumers){
        this.consumers = consumers;
        return this;
    }

    public ChannelBuilder setConnector(Connector connector){
        this.connector = connector;
        return this;
    }

    public ChannelBuilder setShouldExecuteSelf(boolean shouldExecuteSelf) {
        this.shouldExecuteSelf = shouldExecuteSelf;
        return this;
    }

    public Channel build(){
        ChannelConfig channelConfig = new ChannelConfig(channelIndentifier, consumers, connector, shouldExecuteSelf);
        ObjectFactory objectFactory = ServiceLocator.getServiceLocator()
                                        .get(ServiceRegistry.OBJECT_FACTORY, ObjectFactory.class);
        return new Channel(channelConfig);
    }
}
