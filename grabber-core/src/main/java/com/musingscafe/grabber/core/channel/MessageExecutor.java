package com.musingscafe.grabber.core.channel;

import com.musingscafe.grabber.core.GrabberRepository;
import com.musingscafe.grabber.core.Serializer;
import com.musingscafe.grabber.core.connectors.Connector;
import com.musingscafe.grabber.core.consumers.Consumer;
import com.musingscafe.grabber.core.message.GrabberMessage;
import com.musingscafe.grabber.core.message.Tuple;
import java.util.List;

/**
 * Created by ayadav on 11/16/16.
 */
public class MessageExecutor implements MessageCompletionHandler {
    private final ChannelExecutionContext channelExecutionContext;

    public MessageExecutor(ChannelExecutionContext channelExecutionContext) {
        this.channelExecutionContext = channelExecutionContext;
    }

    @Override
    public void onCompletion(MessageExecutionContext messageExecutionContext, boolean shouldDelete){
        Serializer serializer = channelExecutionContext.getSerializer();
        GrabberRepository repository = channelExecutionContext.getRepository();
        String identifier = channelExecutionContext.getChannelIdentifier();

        repository.remove(identifier, serializer.serialize(messageExecutionContext.getMessageId()));
    }


    public void handle(String key, GrabberMessage message) {
        Tuple tuple = message.getContent();
        List<Consumer> consumers = channelExecutionContext.getConsumers();
        Connector connector = channelExecutionContext.getConnector();

        if(consumers.size() > 0) {

            //First will always get GrabberMessage
            tuple = consumers.get(0).handle(tuple);

            if (consumers.size() > 1) {
                for (int i = 1; i < consumers.size(); i++) {
                    tuple = consumers.get(i).handle(tuple);
                }
            }
        }

        MessageExecutionContext messageExecutionContext = new MessageExecutionContext();
        messageExecutionContext.setMessageId(key);
        messageExecutionContext.setHeaders(message.getMessageHeaders());

        connector.handle(tuple, this, messageExecutionContext, channelExecutionContext);
    }
}
