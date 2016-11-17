package com.musingscafe.gclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.musingscafe.grabber.core.Employee;
import com.musingscafe.grabber.core.channel.Channel;
import com.musingscafe.grabber.core.channel.ChannelBuilder;
import com.musingscafe.grabber.core.channel.ChannelConfig;
import com.musingscafe.grabber.core.consumers.Consumer;
import com.musingscafe.grabber.core.consumers.PassThroughConsumer;
import com.musingscafe.grabber.core.message.Field;
import com.musingscafe.grabber.core.message.GrabberMessage;
import com.musingscafe.grabber.core.message.Tuple;
import com.musingscafe.newclient.GrabberConnector;
import com.musingscafe.newclient.NewClient;

import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );


        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setHost("http://localhost");
        serverConfig.setPort("8084/gmessage");

        ChannelConfig channelConfig = new ChannelConfig("user");

        ChannelBuilder builder = new ChannelBuilder();
        Channel channel = builder.setChannelIdentifier("user").setConnector(new GrabberConnector(serverConfig))
                .setConsumers(new ArrayList<Consumer>(){{ add(new PassThroughConsumer());}}).build();

        NewClient.open(serverConfig, new ArrayList<Channel>(){{add(channel);}}, NewClient.DB_PATH);

        send(channel);

        int value = 0;
        while (value != 2) {
            Scanner scanner = new Scanner(System.in);
            value = scanner.nextInt();
            for (int i = 0; i < value; i++) {
                send(channel);
            }
        }
    }

    private static void send(Channel grabberChannel) {
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName(UUID.randomUUID().toString());

        LinkedHashMap<String, Object> fields = new LinkedHashMap<>();
        fields.put("employee", employee);
        Tuple tuple = new Tuple(fields);

        GrabberMessage message = new GrabberMessage();
        message.setContent(tuple);
        grabberChannel.write(message);
    }
}
