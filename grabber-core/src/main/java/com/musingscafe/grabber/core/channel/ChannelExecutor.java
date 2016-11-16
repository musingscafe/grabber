package com.musingscafe.grabber.core.channel;

import com.musingscafe.grabber.core.KeyValuePair;
import com.musingscafe.grabber.core.Producer;
import com.musingscafe.grabber.core.message.GrabberMessage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ayadav on 11/15/16.
 */
public class ChannelExecutor {
    private final ChannelExecutionContext channelExecutionContext;
    private MessageExecutor messageExecutor;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public ChannelExecutor(ChannelExecutionContext channelExecutionContext) {
        this.channelExecutionContext = channelExecutionContext;
        this.messageExecutor = newMessageExecutor();

        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                produce();
            }
        }, 100, 100, TimeUnit.MILLISECONDS);
    }

    private MessageExecutor newMessageExecutor() {
        return new MessageExecutor(this.channelExecutionContext);
    }

    //write to db
    public void write(GrabberMessage message){
        channelExecutionContext.getRepository().save(channelExecutionContext.getChannelIdentifier(), message);
    }

    private void produce(){
        Producer producer = channelExecutionContext.getProducer();

        while (producer.hasNext()){
            KeyValuePair keyValuePair = producer.next();

            //submit
            messageExecutor.handle(keyValuePair.getKey() ,keyValuePair.getValue());
        }
    }
}
