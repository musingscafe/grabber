package com.musingscafe.grabber.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class GrabberMessage <T extends Serializable> implements Serializable {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String VERSION_KEY = "grabber-version";
    private static final String VERSION = "1.0";
    private static final long serialVersionUID = 1L;

    private static final List<String> reservedHeaders = new ArrayList<String>();

    private Map<String, String> messageHeaders;
    private T body;
    private final String messageId;

    public GrabberMessage() {
        messageHeaders = new HashMap<>();
        setDefaultHeaders();
        messageId = UUID.randomUUID().toString();
    }

    public GrabberMessage(Map<String, String> headers, T body) {
        Validate.notNull(headers);
        Validate.notNull(body);

        setMessageHeaders(headers);
        setDefaultHeaders();
        this.body = body;
        messageId = UUID.randomUUID().toString();
    }

    public List<String> getHeaderNames(){
        return messageHeaders.keySet()
                .stream()
                .map(e -> e)
                .collect(Collectors.toList());
    }

    public String getHeader(String key){
        Optional<String> value = messageHeaders.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(key))
                .map(entry -> entry.getValue())
                .findFirst();
        if(value.isPresent()){
            return value.get();
        }

        return null;
    }

    public void addHeader(String key, String value){
        if(!isValid(key)){
            throw new IllegalArgumentException("usage: null or restricted key.");
        }
        messageHeaders.put(key, value);
    }

    public Map<String, String> getMessageHeaders() {
        return messageHeaders;
    }

    public void setMessageHeaders(Map<String, String> headers){
        Validate.notNull(headers);

        headers.entrySet()
                .stream()
                .filter(entry -> isValid(entry.getKey()))
                .forEach(entry -> addHeader(entry.getKey(), entry.getValue()));

        messageHeaders = headers;
    }

    private boolean isValid(String key) {
        return !StringUtils.isEmpty(key) && reservedHeaders.contains(key);
    }

    private void setDefaultHeaders() {
        messageHeaders.put(VERSION_KEY, VERSION);
    }

    static {
        reservedHeaders.add(VERSION_KEY);
    }

    public String getMessageId() {
        return messageId;
    }

    public T getBody() {
        return body;
    }
}
