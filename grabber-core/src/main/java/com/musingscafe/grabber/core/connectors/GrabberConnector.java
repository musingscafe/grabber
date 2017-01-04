package com.musingscafe.grabber.core.connectors;

import com.musingscafe.grabber.core.Serializer;
import com.musingscafe.grabber.core.channel.MessageCompletionHandler;
import com.musingscafe.grabber.core.GrabberMessage;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Created by ayadav on 11/17/16.
 */
public class GrabberConnector implements Connector {
    private final ServerConfig serverConfig;
    private final Serializer serializer;
    public GrabberConnector(ServerConfig serverConfig, Serializer serializer){
        this.serverConfig = serverConfig;
        this.serializer = serializer;
    }

    @Override
    public void handle(GrabberMessage grabberMessage, MessageCompletionHandler messageCompletionHandler) {
        try(CloseableHttpClient httpclient = HttpClients.createDefault()){

            HttpPost httpPost = new HttpPost(serverConfig.getUrlString());

            ByteArrayEntity entity = new ByteArrayEntity(serializer.serialize(grabberMessage));
            httpPost.setEntity(entity);

            httpPost.addHeader("iteratorKey", grabberMessage.getMessageId());

            try(CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost)){
                //System.out.println(closeableHttpResponse.getStatusLine());

                if (closeableHttpResponse.getStatusLine().getStatusCode() == 200){
                    messageCompletionHandler.onCompletion(grabberMessage, true);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        messageCompletionHandler.onCompletion(grabberMessage, false);
    }
}
