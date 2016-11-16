package com.musingscafe.grabber.verticles;

import com.musingscafe.grabber.core.Employee;
import com.musingscafe.grabber.core.message.GrabberMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import org.apache.commons.lang.SerializationUtils;

/**
 * Created by ayadav on 11/14/16.
 */
public class GrabberVertical extends AbstractVerticle {

    private HttpServer httpServer;
    private Router router;
    private EventBus eventBus;

    @Override
    public void start(Future<Void> startFuture){
        HttpServerOptions httpServerOptions = new HttpServerOptions();
        httpServerOptions.setPort(8084);

        httpServer = vertx.createHttpServer(httpServerOptions);
        router = Router.router(vertx);
        eventBus = vertx.eventBus();


        BridgeOptions options = new BridgeOptions().addOutboundPermitted(new PermittedOptions().setAddress("news-feed"));

        router.route().handler(BodyHandler.create());
        router.post("/gmessage").handler(routingContext -> {
             Buffer buffer = routingContext.getBody();

            byte[] bytes = buffer.getBytes();

            GrabberMessage message = (GrabberMessage) SerializationUtils.deserialize(bytes);
            Employee employee = message.getContent().getObject(0, Employee.class);
            System.out.println(employee.getName());

            //eventBus.publish("grabber-message-feed", bytes);

            routingContext.response().end("received");
        });

        router.route("/eventbus/*").handler(SockJSHandler.create(vertx).bridge(options, event -> {

            // You can also optionally provide a handler like this which will be passed any events that occur on the bridge
            // You can use this for monitoring or logging, or to change the raw messages in-flight.
            // It can also be used for fine grained access control.

            if (event.type() == BridgeEventType.SOCKET_CREATED) {
                System.out.println("A socket was created");
            }

            // This signals that it's ok to process the event
            event.complete(true);

        }));

        // Serve the static resources
        router.route("/static/*").handler(StaticHandler.create().setWebRoot("webroot/static"));

        router.route("/").handler(routingContext -> {
            routingContext.response().sendFile("webroot/index.html");
        });

        router.route("/v2").handler(routingContext -> {
            vertx.eventBus().publish("news-feed", "news from the server!");
            routingContext.response().end("Vertx v2 - Hello World");
        });

        httpServer.requestHandler(router::accept).listen(8084);
        System.out.println("starting grabber server");
    }

    @Override
    public void stop(Future<Void> stopFuture){
        httpServer.close();
        System.out.println("stopping grabber server");
    }
}
