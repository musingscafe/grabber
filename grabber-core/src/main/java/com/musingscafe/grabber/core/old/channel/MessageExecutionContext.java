package com.musingscafe.grabber.core.old.channel;

import java.util.Map;

/**
 * Created by ayadav on 11/16/16.
 */
public class MessageExecutionContext {
    private String messageId;
    private Map<String, String> headers;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
