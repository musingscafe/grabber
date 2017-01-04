package com.musingscafe.grabber.core.executors;

import com.musingscafe.grabber.core.Channel;
import com.musingscafe.grabber.core.ChannelConfig;
import com.musingscafe.grabber.core.Serializer;
import com.musingscafe.grabber.core.channel.ChannelExecutionContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ayadav on 11/16/16.
 */
public class ExecutionContext {
    private List<Channel> channels;
    private List<ChannelConfig> channelConfigs;
    private final Serializer serializer;

    public ExecutionContext(List<Channel> channels, Serializer serializer){
        this.serializer = serializer;
        assert (channels != null);
        this.channels = channels;

        setUpChannelConfigs(channels);
        setUpChannelExecutionContexts();
    }

    private void setUpChannelConfigs(List<Channel> channels) {
        this.channelConfigs = channels.stream()
                .map(this::newChannelConfig)
                .collect(Collectors.toList());
    }

    private void setUpChannelExecutionContexts() {
        for (Channel channel: channels){
            channel.setChannelExecutionContext(newChannelExecutionContext(channel));
        }
    }

    private ChannelExecutionContext newChannelExecutionContext(Channel channel){
        return new ChannelExecutionContext(channel.getChannelIdentifier(), channel.getConsumers(), channel.getConnector(), serializer);
    }

    private ChannelConfig newChannelConfig(Channel channel){
        return new ChannelConfig(channel.getChannelIdentifier());
    }
}
