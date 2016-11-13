package com.musingscafe.gclient;

/**
 * Created by ayadav on 11/12/16.
 */
public class ServerConfig {
    private String host;
    private String port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUrlString(){
        return host + ":" + port;
    }
}
