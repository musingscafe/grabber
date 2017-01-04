package com.musingscafe.grabber.core;

import com.musingscafe.grabber.core.channel.ChannelConfig;

import java.io.Closeable;
import java.util.List;

/**
 * Created by ayadav on 11/15/16.
 */
public interface Producer extends Closeable{
    //List<KeyValuePair> getBatch(ChannelConfig channelConfig);
//    boolean hasNext();
    List<GrabberMessage> getBatch(ChannelConfig channelConfig);
}
