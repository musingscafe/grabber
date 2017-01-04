package com.musingscafe.grabber.core.old.connectors;

import com.musingscafe.grabber.core.old.channel.ChannelExecutionContext;
import com.musingscafe.grabber.core.old.channel.MessageCompletionHandler;
import com.musingscafe.grabber.core.old.channel.MessageExecutionContext;
import com.musingscafe.grabber.core.old.message.Tuple;

/**
 * Created by ayadav on 11/15/16.
 */
public interface Connector {
    void handle(Tuple tuple, MessageCompletionHandler messageCompletionHandler, MessageExecutionContext messageExecutionContext, ChannelExecutionContext channelExecutionContext);
}
