package com.musingscafe.grabber.core.channel;

import com.musingscafe.grabber.core.GrabberRepository;
import com.musingscafe.grabber.core.KeyValuePair;
import com.musingscafe.grabber.core.Producer;
import com.musingscafe.grabber.core.message.GrabberMessage;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ayadav on 11/15/16.
 */
public class ChannelExecutor {
    private final ChannelExecutionContext channelExecutionContext;
    private MessageExecutor messageExecutor;
    private final boolean executeSelf;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private List<String> deletionQueue = new LinkedList<>();

    public ChannelExecutor(ChannelExecutionContext channelExecutionContext, boolean executeSelf) {
        this.channelExecutionContext = channelExecutionContext;
        this.executeSelf = executeSelf;
        this.messageExecutor = newMessageExecutor();

        if(executeSelf){
            scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    produce();
                }
            }, 100, 100, TimeUnit.MILLISECONDS);
        }

    }

    private MessageExecutor newMessageExecutor() {
        return new MessageExecutor(this.channelExecutionContext, deletionQueue);
    }

    //write to db
    public void write(GrabberMessage message){
        channelExecutionContext.getRepository().save(channelExecutionContext.getChannelIdentifier(), message);
    }

    public void produce(){
        Producer producer = channelExecutionContext.getProducer();

        List<KeyValuePair> list = producer.getBatch();

        for (KeyValuePair keyValuePair: list) {
            //submit
            messageExecutor.handle(keyValuePair.getKey() ,keyValuePair.getValue());
        }


        GrabberRepository repository = channelExecutionContext.getRepository();
        int i = 0;
        while (!deletionQueue.isEmpty()){
            String key = deletionQueue.remove(i);
            repository.remove(channelExecutionContext.getChannelIdentifier(), key.getBytes(StandardCharsets.ISO_8859_1));
        }
    }
}
