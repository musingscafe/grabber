package com.musingscafe.grabber.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;

/**
 * Created by ayadav on 11/14/16.
 */
public class GrabberVertical extends AbstractVerticle {

    private HttpServer httpServer;

    @Override
    public void start(Future<Void> startFuture){
        HttpServerOptions httpServerOptions = new HttpServerOptions();
        httpServerOptions.setPort(8888);

        httpServer = vertx.createHttpServer(httpServerOptions);

        httpServer.requestHandler(httpServerRequest -> {

            httpServerRequest.response().end("<h1>Hey World</h1>");
        }).listen(8888, result -> {
            if(result.succeeded()){
                startFuture.complete();
            }
            else {
                startFuture.fail(result.cause());
            }
        });
        System.out.println("starting grabber server");
    }

    @Override
    public void stop(Future<Void> stopFuture){
        httpServer.close();
        System.out.println("stopping grabber server");
    }
}
