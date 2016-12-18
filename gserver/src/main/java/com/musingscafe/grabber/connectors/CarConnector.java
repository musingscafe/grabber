package com.musingscafe.grabber.connectors;

import com.musingscafe.grabber.ICache;
import com.musingscafe.grabber.RedisCache;
import com.musingscafe.grabber.core.Employee;
import com.musingscafe.grabber.core.channel.ChannelExecutionContext;
import com.musingscafe.grabber.core.channel.MessageCompletionHandler;
import com.musingscafe.grabber.core.channel.MessageExecutionContext;
import com.musingscafe.grabber.core.connectors.Connector;
import com.musingscafe.grabber.core.message.GrabberMessage;
import com.musingscafe.grabber.core.message.Tuple;

import java.util.UUID;

/**
 * Created by ayadav on 11/17/16.
 */
public class CarConnector implements Connector {
    private ICache cache = new RedisCache();

    @Override
    public void handle(Tuple tuple, MessageCompletionHandler messageCompletionHandler, MessageExecutionContext messageExecutionContext, ChannelExecutionContext channelExecutionContext) {
        GrabberMessage grabberMessage = new GrabberMessage();
        grabberMessage.setMessageHeaders(messageExecutionContext.getHeaders());
        grabberMessage.setContent(tuple);

        cache.put(UUID.randomUUID().toString(), grabberMessage);

        messageCompletionHandler.onCompletion(messageExecutionContext, true);
    }
}
