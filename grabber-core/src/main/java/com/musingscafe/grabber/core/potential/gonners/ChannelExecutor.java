package com.musingscafe.grabber.core.potential.gonners;

import com.musingscafe.grabber.core.GrabberRepository;
import com.musingscafe.grabber.core.Producer;
import com.musingscafe.grabber.core.GrabberMessage;
import com.musingscafe.grabber.core.channel.ChannelConfig;
import com.musingscafe.grabber.core.channel.MessageExecutor;

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
    public void write(GrabberMessage message, ChannelConfig channelConfig){
        grabberRepository.save(channelConfig.getChannelIdentifier(), message);
    }

//    public void produce(ChannelConfig channelConfig){
//        List<KeyValuePair> list = producer.getBatch(channelConfig);
//
//        for (KeyValuePair keyValuePair: list) {
//            //submit
//            messageExecutor.handle(keyValuePair.getKey() ,keyValuePair.getValue());
//        }
//
//        int i = 0;
//        while (!deletionQueue.isEmpty()){
//            String key = deletionQueue.remove(i);
//            grabberRepository.remove(channelConfig.getChannelIdentifier(), key.getBytes(StandardCharsets.ISO_8859_1));
//        }
//    }
}
