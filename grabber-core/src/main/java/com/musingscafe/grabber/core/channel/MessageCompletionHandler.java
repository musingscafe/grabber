package com.musingscafe.grabber.core.channel;

import com.musingscafe.grabber.core.GrabberMessage;

/**
 * Created by ayadav on 11/16/16.
 */
public interface MessageCompletionHandler {
    void onCompletion(GrabberMessage grabberMessage, boolean shouldDelete);
}
