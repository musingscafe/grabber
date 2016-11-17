package com.musingscafe.grabber.verticles;

import com.musingscafe.grabber.channels.Registrar;
import com.musingscafe.grabber.core.channel.Channel;
import com.musingscafe.grabber.core.message.GrabberMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;
import org.apache.commons.lang.SerializationUtils;

import java.io.InputStream;
import java.util.List;

/**
 * Created by ayadav on 11/16/16.
 */
public class PersistVertical extends AbstractVerticle {

    Router router;
    @Override
    public void start(Future<Void> startFuture) {

        vertx.setPeriodic(500, new Handler<Long>() {

            @Override
            public void handle(Long aLong) {
                List<Channel> channels = Registrar.getInstance().getChannels();

                for (Channel channel: channels){
                    channel.getChannelExecutor().produce();
                }
            }
        });
    }
}
