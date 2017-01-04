package com.musingscafe.grabber.core.old.executors;

import com.musingscafe.grabber.core.old.DefaultSerializer;
import com.musingscafe.grabber.core.old.GrabberRepository;
import com.musingscafe.grabber.core.old.Serializer;
import com.musingscafe.grabber.core.old.ServiceLocator;
import com.musingscafe.grabber.core.old.channel.Channel;
import com.musingscafe.grabber.core.old.channel.ChannelConfig;
import com.musingscafe.grabber.core.old.channel.ChannelExecutionContext;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ayadav on 11/16/16.
 */
public class ExecutionContext {
    private ServiceLocator serviceLocator;
    private GrabberRepository repository;
    private Serializer defaultSerializer;
    private List<Channel> channels;
    private String databasePath;
    private List<ChannelConfig> channelConfigs;

    public ExecutionContext(List<Channel> channels, String databasePath){
        assert (channels != null);

        this.databasePath = databasePath;
        this.serviceLocator = new ServiceLocator();
        this.defaultSerializer = new DefaultSerializer();
        this.channels = channels;

        setUpChannelConfigs(channels);
        setUpRepository();
        setUpChannelExecutionContexts();
    }

    private void setUpChannelConfigs(List<Channel> channels) {
        this.channelConfigs = channels.stream()
                .map(this::newChannelConfig)
                .collect(Collectors.toList());
    }

    private void setUpRepository() {
        repository = new GrabberRepository(databasePath, channelConfigs, defaultSerializer);
    }

    private void setUpChannelExecutionContexts() {
        for (Channel channel: channels){
            channel.setChannelExecutionContext(newChannelExecutionContext(channel));
        }
    }

    private ChannelExecutionContext newChannelExecutionContext(Channel channel){
        return new ChannelExecutionContext(channel.getChannelIdentifier(), repository, channel.getConsumers(), channel.getConnector());
    }

    private ChannelConfig newChannelConfig(Channel channel){
        return new ChannelConfig(channel.getChannelIdentifier());
    }

    public GrabberRepository getRepository() {
        return repository;
    }
}
