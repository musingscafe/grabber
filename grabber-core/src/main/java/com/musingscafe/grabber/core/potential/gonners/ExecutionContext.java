package com.musingscafe.grabber.core.potential.gonners;

import com.musingscafe.grabber.core.channel.Channel;
import com.musingscafe.grabber.core.Serializer;

import java.util.List;

/**
 * Created by ayadav on 11/16/16.
 */
public class ExecutionContext {
    private List<Channel> channels;
    private final Serializer serializer;

    public ExecutionContext(List<Channel> channels, Serializer serializer){
        this.serializer = serializer;
        assert (channels != null);
        this.channels = channels;

    }

}
