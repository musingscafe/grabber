package com.musingscafe.grabber.core;

import com.musingscafe.grabber.core.channel.Channel;
import com.musingscafe.grabber.core.channel.ChannelConfig;
import com.musingscafe.grabber.core.registry.ObjectFactory;
import com.musingscafe.grabber.core.registry.ServiceLocator;
import com.musingscafe.grabber.core.registry.ServiceRegistry;
import org.apache.commons.lang.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ayadav on 11/17/16.
 */
public class GrabberClient implements Closeable {
    private static final GrabberClient client = new GrabberClient();
    public static String DB_PATH = "grabber.db";
    private String dbPath;
    private List<Channel> channels;

    private GrabberClient(){}

    public static GrabberClient instance() {
        return client;
    }

    public GrabberClient open(List<Channel> channels, String dbPath){
        setupEnvironment(channels, dbPath);
        return client;
    }

    private void setupEnvironment(List<Channel> channels, String dbPath) {
        setupDbPath(dbPath);
        setChannelProperties(channels);
    }

    private void setupDbPath(String dbPath) {
        client.dbPath = StringUtils.isEmpty(dbPath) ? DB_PATH : dbPath;
    }

    private void setChannelProperties (List<Channel> channels) {
        client.channels = channels;

        final ObjectFactory objectFactory = ServiceLocator.getServiceLocator().get(ServiceRegistry.OBJECT_FACTORY, ObjectFactory.class);
        final Serializer serializer = objectFactory.getDefaultSerializer();
        final List<ChannelConfig> channelConfigs = getChannelConfigs(channels);
        final GrabberRepository grabberRepository = objectFactory.getRepository(dbPath, channelConfigs, serializer);
        final Producer producer = objectFactory.getRocksDbProducer(serializer, grabberRepository);

        channels.stream()
                .forEach(
                        channel -> {
                            channel.getChannelConfig().setGrabberRepository(grabberRepository);
                            channel.getChannelConfig().setProducer(producer);
                        }
                );
    }

    private List<ChannelConfig> getChannelConfigs(List<Channel> channels) {
        return channels.stream()
                .map(channel -> channel.getChannelConfig())
                .collect(Collectors.toList());

    }

    @Override
    public void close() throws IOException {
        //handle gracefully
    }
}
