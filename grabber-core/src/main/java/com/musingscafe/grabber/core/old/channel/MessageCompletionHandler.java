package com.musingscafe.grabber.core.old.channel;

import com.musingscafe.grabber.core.old.channel.MessageExecutionContext;

/**
 * Created by ayadav on 11/16/16.
 */
public interface MessageCompletionHandler {
    void onCompletion(MessageExecutionContext messageExecutionContext, boolean shouldDelete);
}
