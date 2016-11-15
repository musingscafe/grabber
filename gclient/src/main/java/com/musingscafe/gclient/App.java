package com.musingscafe.gclient;

import com.musingscafe.grabber.core.GrabberMessage;
import com.musingscafe.grabber.core.ChannelConfig;

import java.util.ArrayList;
import java.util.Scanner;

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

        ChannelConfig channelConfig = new ChannelConfig("user", 10);


        GrabberClient grabberClient = GrabberClient.open(serverConfig, new ArrayList<ChannelConfig>(){{ add(channelConfig); }}, "test_grabber.db");

        GrabberChannel grabberChannel = grabberClient.getChannel(channelConfig);

        send(grabberChannel);

        int value = 0;
        while (value != 2) {
            Scanner scanner = new Scanner(System.in);
            value = scanner.nextInt();
            for (int i = 0; i < value; i++) {
                send(grabberChannel);
            }
        }
    }

    private static void send(GrabberChannel grabberChannel) {
        GrabberMessage grabberMessage = new GrabberMessage();
        grabberMessage.addHeader("1", "10");
        grabberMessage.setBody("Hey dude".getBytes());
        grabberChannel.send(grabberMessage);
    }
}
