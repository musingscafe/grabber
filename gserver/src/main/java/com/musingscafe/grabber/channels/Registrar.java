package com.musingscafe.grabber.channels;

import com.musingscafe.grabber.core.channel.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ayadav on 11/17/16.
 */
public class Registrar {
    private static Registrar registrar = new Registrar();
    private Map<String, Channel> channelMap = new HashMap<>();

    private Registrar(){

    }

    public static Registrar getInstance(){
        return registrar;
    }

    public void registerChannel(String identifier, Channel channel){
        channelMap.put(identifier, channel);
    }

    public Channel getChannel(String key){
        return channelMap.get(key);
    }

    public List<Channel> getChannels(){
        return new ArrayList<>(channelMap.values());
    }

    public List<String> getKeys(){
        return new ArrayList<>(channelMap.keySet());
    }
}
