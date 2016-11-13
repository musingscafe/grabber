package com.musingscafe.gclient;

import com.musingscafe.grabber.GrabberMessage;

/**
 * Created by ayadav on 11/12/16.
 */
public class GrabberChannel {
    private final ChannelConfig channelConfig;
    private final GrabberDispatcher grabberDispatcher;

    public GrabberChannel(ChannelConfig channelConfig, GrabberDispatcher grabberDispatcher) {
        this.channelConfig = channelConfig;
        this.grabberDispatcher = grabberDispatcher;
    }

    public void send(GrabberMessage message){
        grabberDispatcher.send(channelConfig.getChannelIdentifier(), message);
    }
}
