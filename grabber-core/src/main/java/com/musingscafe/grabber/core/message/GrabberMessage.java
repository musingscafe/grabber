package com.musingscafe.grabber.core.message;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ayadav on 11/15/16.
 */
public class GrabberMessage implements Serializable {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private Map<String, String> messageHeaders = new HashMap<String, String>();

    //TODO: remove restriction
    private final List<String> reservedHeaders = new ArrayList<String>(){{
        add("identifier");
    }};

    private Tuple content;

    public Tuple getContent() {
        return content;
    }

    public void setContent(Tuple content) {
        this.content = content;
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
//        if(!isValid(key)){
//            throw new IllegalArgumentException("usage: null or restricted key.");
//        }
        messageHeaders.put(key, value);
    }

    private boolean isValid(String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }

        if (reservedHeaders.contains(key)) {
            return false;
        }

        return true;
    }

    public Map<String, String> getMessageHeaders() {
        return messageHeaders;
    }

    //TODO: no validation
    public void setMessageHeaders(Map<String, String> headers){
        messageHeaders = headers;
    }
}
