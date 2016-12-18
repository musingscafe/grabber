package com.musingscafe.grabber;

import com.musingscafe.grabber.channels.Registrar;
import com.musingscafe.grabber.connectors.CarConnector;
import com.musingscafe.grabber.connectors.UserConnector;
import com.musingscafe.grabber.core.GrabberClient;
import com.musingscafe.grabber.core.channel.Channel;
import com.musingscafe.grabber.core.channel.ChannelBuilder;
import com.musingscafe.grabber.core.connectors.GrabberConnector;
import com.musingscafe.grabber.core.connectors.ServerConfig;
import com.musingscafe.grabber.core.consumers.Consumer;
import com.musingscafe.grabber.core.consumers.PassThroughConsumer;
import com.musingscafe.grabber.verticles.GrabberVertical;
import com.musingscafe.grabber.verticles.PersistVertical;
import io.vertx.core.Vertx;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        registerChannels();

        //open client
        GrabberClient.open(Registrar.getInstance().getChannels(), GrabberClient.DB_PATH);

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new GrabberVertical());
        vertx.deployVerticle(new PersistVertical());
    }

    private static void registerChannels() {
        ChannelBuilder builder = new ChannelBuilder();

        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setHost("http://localhost");
        serverConfig.setPort("8084/gmessage");


        Channel defaultChannel = builder.setChannelIdentifier("default").setConnector(new GrabberConnector(serverConfig))
                .setConsumers(new ArrayList<Consumer>(){{ add(new PassThroughConsumer());}}).build();

        Channel channel = builder
                        .setChannelIdentifier("user")
                        .setConnector(new UserConnector())
                        .setConsumers(new ArrayList<Consumer>(){{ add(new PassThroughConsumer());}})
                        .build();

//        Channel carChannel = builder
//                .setChannelIdentifier("car")
//                .setConnector(new CarConnector())
//                .setConsumers(new ArrayList<Consumer>(){{ add(new PassThroughConsumer());}})
//                .build();

        Registrar.getInstance().registerChannel("user", channel);
        //Registrar.getInstance().registerChannel("car", carChannel);
        Registrar.getInstance().registerChannel("defaultChannel", defaultChannel);
    }
}
