package com.musingscafe.grabber.core;

import com.musingscafe.grabber.core.channel.Channel;
import com.musingscafe.grabber.core.executors.Executor;
import org.apache.commons.lang.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * Created by ayadav on 11/17/16.
 */
public class GrabberClientV1 implements Closeable {
    private static final GrabberClientV1 client = new GrabberClientV1();
    public static String DB_PATH = "grabber.db";
    private String dbPath;

    private GrabberClientV1(){
    }

    public static GrabberClientV1 open(List<Channel> channels, String dbPath){
        assert(channels != null);
        assert(channels.size() > 0);

        client.dbPath = dbPath;

        setupEnvironment(channels, client.dbPath);
        return client;
    }

    private static void setupEnvironment(List<Channel> channels, String dbPath) {
        String path = dbPath;
        if(StringUtils.isEmpty(path)){
            path = DB_PATH;
        }
        Executor.open(channels, path);
    }

    @Override
    public void close() throws IOException {
        //handle gracefully
    }
}
