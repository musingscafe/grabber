package com.musingscafe.grabber.handlers;

import com.musingscafe.grabber.channels.Registrar;
import com.musingscafe.grabber.core.GrabberClient;
import com.musingscafe.grabber.core.channel.Channel;
import com.musingscafe.grabber.core.message.GrabberMessage;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayadav on 11/17/16.
 */
public class ByteRequestHandler {

    public static void handle(GrabberMessage message){

        String key = message.getHeader("identifier");
        if(StringUtils.isEmpty(key)){
            key = "defaultChannel";
        }

        Channel channel = Registrar.getInstance().getChannel(key);

        channel.write(message);
    }
}
