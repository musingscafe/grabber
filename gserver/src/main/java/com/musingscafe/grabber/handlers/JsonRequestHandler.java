package com.musingscafe.grabber.handlers;

import com.google.gson.*;
import com.musingscafe.grabber.channels.Registrar;
import com.musingscafe.grabber.core.channel.Channel;
import com.musingscafe.grabber.core.message.GrabberMessage;
import org.apache.commons.lang.SerializationUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Created by ayadav on 11/17/16.
 */
public class JsonRequestHandler {

    public static void handle(String jsonString){
        System.out.println(jsonString);
    }
}
