package com.musingscafe.newclient;

import com.musingscafe.gclient.ServerConfig;
import com.musingscafe.grabber.core.GrabberRepository;
import com.musingscafe.grabber.core.channel.Channel;
import com.musingscafe.grabber.core.executors.Executor;
import org.apache.commons.lang.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * Created by ayadav on 11/16/16.
 */
public class NewClient implements Closeable {
    private static final NewClient client = new NewClient();
    public static final String DEFAULT_CHANNEL = "grabber.default.channel";
    public static final int POOL_SIZE = 10;
    public static String DB_PATH = "grabber.db";
    private ServerConfig serverConfig;
    private String dbPath;
    private GrabberRepository grabberRepository;
    private Executor executor;

    private NewClient(){
    }

    public static NewClient open(ServerConfig serverConfig, List<Channel> channels, String dbPath){
        assert(serverConfig != null);
        assert(channels != null);
        assert(channels.size() > 0);

        client.serverConfig = serverConfig;
        client.dbPath = dbPath;

        setupEnvironment(channels, dbPath);
        return client;
    }

    private static void setupEnvironment(List<Channel> channels, String dbPath) {
        String path = dbPath;
        if(StringUtils.isEmpty(path)){
            path = DB_PATH;
        }
        client.executor = Executor.open(channels, path);
    }

    @Override
    public void close() throws IOException {
        client.grabberRepository.close();
    }
}
