package com.musingscafe.gclient;

import com.musingscafe.grabber.GrabberMessage;

/**
 * Created by ayadav on 11/12/16.
 */
class GrabberDispatcher {
    private final ServerConfig serverConfig;
    private final int poolSize;
    private final GrabberRepository grabberRepository;

    public GrabberDispatcher(ServerConfig serverConfig, int poolSize, GrabberRepository grabberRepository) {
        this.serverConfig = serverConfig;
        this.poolSize = poolSize;
        this.grabberRepository = grabberRepository;
    }

    public void send(String channelIdentifier, GrabberMessage message) {
        grabberRepository.save(channelIdentifier, message);
    }
}
