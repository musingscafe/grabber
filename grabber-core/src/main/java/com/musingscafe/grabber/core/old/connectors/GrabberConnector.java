package com.musingscafe.grabber.core.old.connectors;

import com.musingscafe.grabber.core.old.channel.ChannelExecutionContext;
import com.musingscafe.grabber.core.old.channel.MessageCompletionHandler;
import com.musingscafe.grabber.core.old.channel.MessageExecutionContext;
import com.musingscafe.grabber.core.old.message.GrabberMessage;
import com.musingscafe.grabber.core.old.message.Tuple;
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
    private ServerConfig serverConfig;

    public GrabberConnector(ServerConfig serverConfig){
        this.serverConfig = serverConfig;
    }

    @Override
    public void handle(Tuple tuple, MessageCompletionHandler messageCompletionHandler, MessageExecutionContext messageExecutionContext, ChannelExecutionContext channelExecutionContext) {
        try(CloseableHttpClient httpclient = HttpClients.createDefault()){

            HttpPost httpPost = new HttpPost(serverConfig.getUrlString());
            GrabberMessage grabberMessage = new GrabberMessage();
            grabberMessage.setMessageHeaders(messageExecutionContext.getHeaders());
            grabberMessage.setContent(tuple);

            ByteArrayEntity entity = new ByteArrayEntity(channelExecutionContext.getSerializer().serialize(grabberMessage));
            httpPost.setEntity(entity);

            httpPost.addHeader("iteratorKey", messageExecutionContext.getMessageId());

            try(CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost)){
                //System.out.println(closeableHttpResponse.getStatusLine());

                if (closeableHttpResponse.getStatusLine().getStatusCode() == 200){
                    messageCompletionHandler.onCompletion(messageExecutionContext, true);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        messageCompletionHandler.onCompletion(messageExecutionContext, false);
    }
}
