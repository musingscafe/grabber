package com.musingscafe.gclient;

import com.musingscafe.grabber.core.GrabberRepository;
import com.musingscafe.grabber.core.message.GrabberMessage;

/**
 * Created by ayadav on 11/12/16.
 */
class GrabberDispatcher {
    private final ServerConfig serverConfig;
    private final GrabberRepository grabberRepository;

    public GrabberDispatcher(ServerConfig serverConfig, GrabberRepository grabberRepository) {
        this.serverConfig = serverConfig;
        this.grabberRepository = grabberRepository;
    }

    public void send(String channelIdentifier, GrabberMessage message) {
        grabberRepository.save(channelIdentifier, message);
    }
}
