package com.musingscafe.gclient;

/**
 * Created by ayadav on 11/12/16.
 */
public class ChannelConfig {
    private final String channelIdentifier;
    private final int poolSize;
    private final byte[] channelIdentifierBytes;

    public ChannelConfig(String channelIdentifier, int poolSize) {
        this.channelIdentifier = channelIdentifier;
        this.poolSize = poolSize;
        this.channelIdentifierBytes = this.channelIdentifier.getBytes();
    }

    public String getChannelIdentifier() {
        return channelIdentifier;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public byte[] getChannelIdentifierBytes(){
        return this.channelIdentifierBytes;
    }
}
