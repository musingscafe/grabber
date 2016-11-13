package com.musingscafe.gclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Created by ayadav on 11/13/16.
 */
public class SendTask implements Runnable {

    private final String url;
    private final byte[] key;
    private final byte[] message;
    private final String identifier;
    private final Callable callable;

    public SendTask(String url, byte[] key, byte[] message, String identifier, Callable callable) {
        this.url = url;
        this.key = key;
        this.message = message;
        this.identifier = identifier;
        this.callable = callable;
    }

    @Override
    public void run() {
        try(CloseableHttpClient httpclient = HttpClients.createDefault()){

            HttpPost httpPost = new HttpPost(url);
            ByteArrayEntity entity = new ByteArrayEntity(message);
            httpPost.setEntity(entity);

            try(CloseableHttpResponse closeableHttpResponse = httpclient.execute(httpPost)){
                System.out.println(closeableHttpResponse.getStatusLine());

                if (closeableHttpResponse.getStatusLine().getStatusCode() == 200){
                    callable.onTaskCompletion(identifier, key,  true);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        callable.onTaskCompletion(identifier, key, false);
    }
}
