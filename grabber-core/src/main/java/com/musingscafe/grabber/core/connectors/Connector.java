package com.musingscafe.grabber.core.connectors;

import com.musingscafe.grabber.core.channel.MessageCompletionHandler;
import com.musingscafe.grabber.core.GrabberMessage;

/**
 * Created by ayadav on 11/15/16.
 */
public interface Connector {
    //void handle(Tuple tuple, MessageCompletionHandler messageCompletionHandler, MessageExecutionContext messageExecutionContext, ChannelExecutionContext channelExecutionContext);
    void handle(GrabberMessage grabberMessage, MessageCompletionHandler messageCompletionHandler);
}
