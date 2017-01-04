package com.musingscafe.grabber.core.executors;

import com.musingscafe.grabber.core.Channel;
import com.musingscafe.grabber.core.GrabberRepository;
import com.musingscafe.grabber.core.Producer;
import com.musingscafe.grabber.core.Serializer;
import com.musingscafe.grabber.core.channel.ChannelExecutionContext;
import com.musingscafe.grabber.core.channel.ChannelExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ayadav on 11/16/16.
 */
public class Executor {
    private static Executor executor = new Executor();
    public ExecutionContext executionContext;
    private List<Channel> channels;
    private Map<String, Channel> channelMap = new HashMap<>();

    private Executor(){}

    public static Executor open(List<Channel> channels, String databasePath, Serializer serializer, GrabberRepository grabberRepository, Producer producer) {

        executor.channels = channels;

        setupExecutionContext(databasePath, serializer);
        setupChannels(grabberRepository, producer, serializer);

        return executor;
    }

    private static void setupExecutionContext(String databasePath, Serializer serializer) {
        executor.executionContext = new ExecutionContext(executor.channels, serializer);
    }

    private static void setupChannels(GrabberRepository grabberRepository, Producer producer, Serializer serializer){
        for (Channel channel: executor.channels){
            channel.setChannelExecutionContext(newChannelExecutionContext(channel, serializer));
            channel.setChannelExecutor(newChannelExecutor(channel, grabberRepository, producer));
            executor.channelMap.put(channel.getChannelIdentifier(), channel);
        }
    }

    private static ChannelExecutionContext newChannelExecutionContext(Channel channel, Serializer serializer) {
        return new ChannelExecutionContext(channel.getChannelIdentifier(),
                    channel.getConsumers(), channel.getConnector(), serializer);
    }

    private static ChannelExecutor newChannelExecutor(Channel channel, GrabberRepository grabberRepository, Producer producer) {
        return new ChannelExecutor(channel.getChannelExecutionContext(), grabberRepository, producer);
    }

    public Channel getChannel(String key){
        return channelMap.get(key);
    }
}
