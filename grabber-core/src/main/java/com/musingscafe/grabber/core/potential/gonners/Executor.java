package com.musingscafe.grabber.core.potential.gonners;

import com.musingscafe.grabber.core.channel.Channel;
import com.musingscafe.grabber.core.GrabberRepository;
import com.musingscafe.grabber.core.Producer;
import com.musingscafe.grabber.core.Serializer;

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

    public static Executor open(List<Channel> channels, Serializer serializer, GrabberRepository grabberRepository, Producer producer) {

        executor.channels = channels;

        setupExecutionContext(serializer);
        setupChannels(grabberRepository, producer, serializer);

        return executor;
    }

    private static void setupExecutionContext(Serializer serializer) {
        executor.executionContext = new ExecutionContext(executor.channels, serializer);
    }

    private static void setupChannels(GrabberRepository grabberRepository, Producer producer, Serializer serializer){
        for (Channel channel: executor.channels){
            executor.channelMap.put(channel.getChannelConfig().getChannelIdentifier(), channel);
        }
    }

    public Channel getChannel(String key){
        return channelMap.get(key);
    }
}
