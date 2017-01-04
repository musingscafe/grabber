package com.musingscafe.grabber.core.old.consumers;

import com.musingscafe.grabber.core.old.message.GrabberMessage;
import com.musingscafe.grabber.core.old.message.Tuple;

/**
 * Created by ayadav on 11/15/16.
 */
public class PassThroughConsumer implements Consumer {

    @Override
    public Tuple handle(Tuple tuple) {
        return tuple;
    }
}
