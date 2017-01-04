package com.musingscafe.grabber.core.consumers;

import com.musingscafe.grabber.core.GrabberMessage;

/**
 * Created by ayadav on 11/15/16.
 */
public class PassThroughConsumer implements Consumer {

    @Override
    public GrabberMessage handle(GrabberMessage grabberMessage) {
        return grabberMessage;
    }
}
