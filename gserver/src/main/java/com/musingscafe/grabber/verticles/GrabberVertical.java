package com.musingscafe.grabber.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeEventType;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.PermittedOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

/**
 * Created by ayadav on 11/14/16.
 */
public class GrabberVertical extends AbstractVerticle {

    private HttpServer httpServer;
    private Router router;

    @Override
    public void start(Future<Void> startFuture){
        HttpServerOptions httpServerOptions = new HttpServerOptions();
        httpServerOptions.setPort(8888);

        httpServer = vertx.createHttpServer(httpServerOptions);
        router = Router.router(vertx);

        BridgeOptions options = new BridgeOptions().addOutboundPermitted(new PermittedOptions().setAddress("news-feed"));

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

        httpServer.requestHandler(router::accept).listen(8888);
        System.out.println("starting grabber server");
    }

    @Override
    public void stop(Future<Void> stopFuture){
        httpServer.close();
        System.out.println("stopping grabber server");
    }
}
