package com.musingscafe.grabber.core.consumers;

import com.musingscafe.grabber.core.message.Tuple;

/**
 * Created by ayadav on 11/15/16.
 */
public interface Consumer {
    Tuple handle(Tuple tuple);
}
