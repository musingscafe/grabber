package com.musingscafe.grabber.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.musingscafe.grabber.core.message.GrabberMessage;

/**
 * Created by ayadav on 11/17/16.
 */
public class JsonRequestHandler {
    public void handle(String jsonString){
        Gson gson = new GsonBuilder().create();
        GrabberMessage message = gson.fromJson(jsonString, GrabberMessage.class);

    }
}
