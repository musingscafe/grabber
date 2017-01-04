package com.musingscafe.grabber.core.channel;

import com.musingscafe.grabber.core.GrabberRepository;
import com.musingscafe.grabber.core.KeyValuePair;
import com.musingscafe.grabber.core.Producer;
import com.musingscafe.grabber.core.GrabberMessage;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ayadav on 11/15/16.
 */
public class ChannelExecutor {
    private final ChannelExecutionContext channelExecutionContext;
    private MessageExecutor messageExecutor;
    private List<String> deletionQueue = new LinkedList<>();
    private GrabberRepository grabberRepository;
    private Producer producer;

    public ChannelExecutor(ChannelExecutionContext channelExecutionContext,
                           GrabberRepository grabberRepository,
                           Producer producer) {
        this.channelExecutionContext = channelExecutionContext;
        this.messageExecutor = newMessageExecutor();
        this.grabberRepository = grabberRepository;
        this.producer = producer;

        //TODO: this is client's work or create a separate forwarder
//        if(this.executeSelf){
//            scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
//                @Override
//                public void run() {
//                    produce();
//                }
//            }, 100, 100, TimeUnit.MILLISECONDS);
//        }

    }

    private MessageExecutor newMessageExecutor() {
        return new MessageExecutor(channelExecutionContext.getConsumers(), channelExecutionContext.getConnector(), deletionQueue, grabberRepository);
    }

    //write to db
    public void write(GrabberMessage message){
        grabberRepository.save(channelExecutionContext.getChannelIdentifier(), message);
    }

    public void produce(){
        List<KeyValuePair> list = producer.getBatch();

        for (KeyValuePair keyValuePair: list) {
            //submit
            messageExecutor.handle(keyValuePair.getKey() ,keyValuePair.getValue());
        }

        int i = 0;
        while (!deletionQueue.isEmpty()){
            String key = deletionQueue.remove(i);
            grabberRepository.remove(channelExecutionContext.getChannelIdentifier(), key.getBytes(StandardCharsets.ISO_8859_1));
        }
    }
}
