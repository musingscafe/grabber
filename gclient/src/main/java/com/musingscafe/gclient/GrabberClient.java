package com.musingscafe.gclient;

import com.musingscafe.grabber.core.ChannelConfig;
import com.musingscafe.grabber.core.GrabberRepository;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Created by ayadav on 11/12/16.
 */
public class GrabberClient implements Closeable{
    private static final GrabberClient client = new GrabberClient();
    public static final String DEFAULT_CHANNEL = "grabber.default.channel";
    public static final int POOL_SIZE = 10;
    public static String DB_PATH = "grabber.db";
    private ServerConfig serverConfig;
    private List<ChannelConfig> channelConfigs;
    private final Map<String, GrabberChannel> channelMap = new HashMap<>();
    private String dbPath;
    private GrabberRepository grabberRepository;
    private Sender sender;

    private GrabberClient(){
    }

    public static GrabberClient open(ServerConfig serverConfig, List<ChannelConfig> channelConfigs, String dbPath){
        assert(serverConfig != null);
        assert(channelConfigs != null);
        assert(channelConfigs.size() > 0);

        client.channelConfigs = channelConfigs;
        client.serverConfig = serverConfig;
        client.dbPath = dbPath;

        //lets add it in repo
        //addDefaultChannelConfig();

        setupEnvironment();
        return client;
    }

    private static void setupEnvironment() {
        client.grabberRepository = new GrabberRepository(client.dbPath, client.channelConfigs);
        client.sender = new Sender(client.grabberRepository, client.serverConfig, POOL_SIZE);

        Executors.newFixedThreadPool(1).submit(() -> client.sender.startServer());

        for (ChannelConfig channelConfig: client.channelConfigs){
            GrabberDispatcher grabberDispatcher = new GrabberDispatcher(client.serverConfig, channelConfig.getPoolSize(), client.grabberRepository);
            client.channelMap.put(channelConfig.getChannelIdentifier(), new GrabberChannel(channelConfig, grabberDispatcher));
        }
    }

    public static GrabberClient open(ServerConfig serverConfig){
        List<ChannelConfig> channelConfigs = new ArrayList<>();
        channelConfigs.add(new ChannelConfig(DEFAULT_CHANNEL, POOL_SIZE));
        return open(serverConfig, channelConfigs, DB_PATH);
    }

    public GrabberChannel getChannel(String channelIdentifier){
        return channelMap.get(channelIdentifier);
    }

    public GrabberChannel getChannel(ChannelConfig channelConfig){
        return channelMap.get(channelConfig.getChannelIdentifier());
    }

    private static void addDefaultChannelConfig() {
        boolean exists = false;
        for (ChannelConfig channelConfig: client.channelConfigs){
            if (channelConfig.getChannelIdentifier().equals(DEFAULT_CHANNEL)){
                exists = true;
            }
        }
        if(!exists){
            client.channelConfigs.add(new ChannelConfig(DEFAULT_CHANNEL, POOL_SIZE));
        }
    }

    @Override
    public void close() throws IOException {
        client.grabberRepository.close();
        client.sender.close();
    }
}
