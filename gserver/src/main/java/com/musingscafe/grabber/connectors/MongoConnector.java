package com.musingscafe.grabber.connectors;

import com.musingscafe.grabber.core.Employee;
import com.musingscafe.grabber.core.channel.ChannelExecutionContext;
import com.musingscafe.grabber.core.channel.MessageCompletionHandler;
import com.musingscafe.grabber.core.channel.MessageExecutionContext;
import com.musingscafe.grabber.core.connectors.Connector;
import com.musingscafe.grabber.core.message.Tuple;

/**
 * Created by ayadav on 11/17/16.
 */
public class MongoConnector implements Connector {
    @Override
    public void handle(Tuple tuple, MessageCompletionHandler messageCompletionHandler, MessageExecutionContext messageExecutionContext, ChannelExecutionContext channelExecutionContext) {
        System.out.println(tuple.getObject(0, Employee.class).getName());
    }
}
