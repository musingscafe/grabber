package com.musingscafe.grabber.core.channel;

import com.musingscafe.grabber.core.GrabberRepository;
import com.musingscafe.grabber.core.connectors.Connector;
import com.musingscafe.grabber.core.consumers.Consumer;
import com.musingscafe.grabber.core.GrabberMessage;

import java.util.List;

/**
 * Created by ayadav on 11/16/16.
 */
public class MessageExecutor implements MessageCompletionHandler {
    private final List<String> deletionQueue;
    private final GrabberRepository grabberRepository;
    private final List<Consumer> consumers;
    private final Connector connector;

    public MessageExecutor(List<Consumer> consumers,
                           Connector connector,
                           List<String> deletionQueue, GrabberRepository grabberRepository) {
        this.deletionQueue = deletionQueue;
        this.grabberRepository = grabberRepository;
        this.connector = connector;
        this.consumers = consumers;
    }

    @Override
    public void onCompletion(GrabberMessage grabberMessage, boolean shouldDelete){
        deletionQueue.add(grabberMessage.getMessageId());
        //repository.saveToDefaultColumn(messageExecutionContext.getMessageId());
    }


    public void handle(GrabberMessage message) {
        GrabberMessage grabberMessage = message;
        if (consumers != null) {
            for (int i = 0; i < consumers.size(); i++) {
                grabberMessage = consumers.get(i).handle(grabberMessage);
            }
        }
        connector.handle(grabberMessage, this);
    }
}
