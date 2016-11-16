package com.musingscafe.grabber.core.consumers;

import com.musingscafe.grabber.core.message.GrabberMessage;
import com.musingscafe.grabber.core.message.Tuple;

import java.io.Serializable;

/**
 * Created by ayadav on 11/15/16.
 */
public class PassThroughConsumer implements Consumer {

    @Override
    public Tuple handle(Tuple tuple) {
        return tuple;
    }
}
