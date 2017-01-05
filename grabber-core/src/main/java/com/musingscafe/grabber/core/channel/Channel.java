package com.musingscafe.grabber.core.channel;

import com.musingscafe.grabber.core.GrabberMessage;
import com.musingscafe.grabber.core.GrabberRepository;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ayadav on 11/15/16.
 */
public class Channel {
    private ChannelConfig channelConfig;
    private MessageExecutor messageExecutor;
    private List<String> deletionQueue = new LinkedList<>();
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    public Channel(ChannelConfig channelConfig) {
        this.channelConfig = channelConfig;
        this.messageExecutor = newMessageExecutor();

        if(this.channelConfig.isShouldExecuteSelf()){
            scheduledExecutorService.scheduleWithFixedDelay(() -> produce(channelConfig), 100, 100, TimeUnit.MILLISECONDS);
        }
    }

    //write to db
    public void write(GrabberMessage message){
        final GrabberRepository grabberRepository = channelConfig.getGrabberRepository();
        grabberRepository.save(channelConfig.getChannelIdentifier(), message);
    }

    public ChannelConfig getChannelConfig() {
        return channelConfig;
    }

    private MessageExecutor newMessageExecutor() {
        return new MessageExecutor(channelConfig.getConsumers(), channelConfig.getConnector(), deletionQueue, channelConfig.getGrabberRepository());
    }

    public void produce(ChannelConfig channelConfig){
        List<GrabberMessage> list = channelConfig.getProducer().getBatch(channelConfig);

        list.stream()
                .forEach(grabberMessage -> messageExecutor.handle(grabberMessage));

        //TODO: debug why we wrote this one. Why i is only set to 0?
        int i = 0;
        while (!deletionQueue.isEmpty()){
            String key = deletionQueue.remove(i);
            channelConfig.getGrabberRepository().remove(channelConfig.getChannelIdentifier(), key.getBytes(StandardCharsets.ISO_8859_1));
        }
    }
}
