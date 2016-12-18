package com.musingscafe.gclient;

import com.musingscafe.grabber.core.Employee;
import com.musingscafe.grabber.core.GrabberClient;
import com.musingscafe.grabber.core.channel.Channel;
import com.musingscafe.grabber.core.channel.ChannelBuilder;
import com.musingscafe.grabber.core.channel.ChannelConfig;
import com.musingscafe.grabber.core.connectors.GrabberConnector;
import com.musingscafe.grabber.core.connectors.ServerConfig;
import com.musingscafe.grabber.core.consumers.Consumer;
import com.musingscafe.grabber.core.consumers.PassThroughConsumer;
import com.musingscafe.grabber.core.message.GrabberMessage;
import com.musingscafe.grabber.core.message.Tuple;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    Channel channel;
    Channel carChannel;
    private boolean initDone = false;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    public void testApp()
    {
        init();

        send(channel);
        //send(carChannel);

        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void init() {

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

            GrabberClient.open(new ArrayList<Channel>() {{
                add(channel);
                add(carChannel);
            }}, GrabberClient.DB_PATH);

            initDone = true;


    }



    private ServerConfig getServerConfig() {
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
