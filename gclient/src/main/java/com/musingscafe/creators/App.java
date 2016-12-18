package com.musingscafe.creators;

import com.musingscafe.grabber.core.Employee;
import com.musingscafe.grabber.core.GrabberClient;
import com.musingscafe.grabber.core.channel.Channel;
import com.musingscafe.grabber.core.channel.ChannelBuilder;
import com.musingscafe.grabber.core.connectors.GrabberConnector;
import com.musingscafe.grabber.core.connectors.ServerConfig;
import com.musingscafe.grabber.core.consumers.Consumer;
import com.musingscafe.grabber.core.consumers.PassThroughConsumer;
import com.musingscafe.grabber.core.message.GrabberMessage;
import com.musingscafe.grabber.core.message.Tuple;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by ayadav on 11/17/16.
 */
public class App {

    static Channel channel;
    static Channel carChannel;
    private static boolean initDone = false;

    public static void main(String[] args){

        testApp();


        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
    public static void testApp()
    {
        init();

        send(channel);
        send(carChannel);

    }



    private static void init() {

        ServerConfig serverConfig = getServerConfig();

        ChannelBuilder builder = new ChannelBuilder();
        channel = builder.setChannelIdentifier("user").setConnector(new GrabberConnector(serverConfig))
                .setConsumers(new ArrayList<Consumer>() {{
                    add(new PassThroughConsumer());
                }}).build();

        carChannel = builder.setChannelIdentifier("car").setConnector(new GrabberConnector(serverConfig))
                .setConsumers(new ArrayList<Consumer>() {{
                    add(new PassThroughConsumer());
                }}).build();

        channel.setExecuteSelf(true);
        carChannel.setExecuteSelf(true);

        GrabberClient.open(new ArrayList<Channel>() {{
            add(channel);
            add(carChannel);
        }}, GrabberClient.DB_PATH);

        initDone = true;


    }



    private static ServerConfig getServerConfig() {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setHost("http://localhost");
        serverConfig.setPort("8084/gmessage");
        return serverConfig;
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
