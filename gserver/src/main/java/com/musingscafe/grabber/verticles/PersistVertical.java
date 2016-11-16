package com.musingscafe.grabber.verticles;

import com.musingscafe.grabber.core.message.GrabberMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;
import org.apache.commons.lang.SerializationUtils;

import java.io.InputStream;

/**
 * Created by ayadav on 11/16/16.
 */
public class PersistVertical extends AbstractVerticle {

    Router router;
    @Override
    public void start(Future<Void> startFuture) {

        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("grabber-message-feed", objectMessage -> {
            GrabberMessage message = (GrabberMessage) SerializationUtils.deserialize((InputStream) objectMessage);
            //persist
            System.out.println("Persist : " + message.getHeader("identifier"));
        });
    }
}
