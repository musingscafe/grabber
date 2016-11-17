package com.musingscafe.grabber.core.executors;

import com.musingscafe.grabber.core.channel.Channel;
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

    public static Executor open(List<Channel> channels, String databasePath){

        executor.channels = channels;

        setupExecutionContext(databasePath);
        setupChannels();

        return executor;
    }

    private static void setupExecutionContext(String databasePath) {
        executor.executionContext = new ExecutionContext(executor.channels, databasePath);
    }

    private static void setupChannels(){
        for (Channel channel: executor.channels){
            channel.setChannelExecutionContext(newChannelExecutionContext(channel));
            channel.setChannelExecutor(newChannelExecutor(channel));
            executor.channelMap.put(channel.getChannelIdentifier(), channel);
        }
    }

    private static ChannelExecutionContext newChannelExecutionContext(Channel channel) {
        return new ChannelExecutionContext(channel.getChannelIdentifier(),
                    executor.executionContext.getRepository(), channel.getConsumers(), channel.getConnector());
    }

    private static ChannelExecutor newChannelExecutor(Channel channel) {
        return new ChannelExecutor(channel.getChannelExecutionContext(), channel.isExecuteSelf());
    }

    public Channel getChannel(String key){
        return channelMap.get(key);
    }
}
