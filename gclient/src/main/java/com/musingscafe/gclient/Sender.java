package com.musingscafe.gclient;

import com.musingscafe.grabber.core.GrabberRepository;
import org.rocksdb.ColumnFamilyHandle;
import org.rocksdb.RocksIterator;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ayadav on 11/13/16.
 */
public class Sender implements Closeable, Callable{
    private boolean keepRunning;
    private Thread producer;
    private final ExecutorService executorService;
    private final GrabberRepository grabberRepository;
    private final ServerConfig serverConfig;
    private final int poolSize;
    private final Map<String, ColumnFamilyHandle> columnFamilyHandleMap;

    public Sender(GrabberRepository grabberRepository, ServerConfig serverConfig, int poolSize){
        this.grabberRepository = grabberRepository;
        this.serverConfig = serverConfig;
        this.poolSize = poolSize;
        this.executorService = Executors.newFixedThreadPool(this.poolSize);
        this.columnFamilyHandleMap = grabberRepository.getColumnFamilyHandleMap();
        producer = new Thread(() -> startServer());
        producer.start();
    }

    public void produce(){

        //TODO: there should be multiple readers
        //read in batch of 10.
        int batchCount = 0;
        List<SendTask> tasks;
        for (Map.Entry<String, ColumnFamilyHandle> entry: columnFamilyHandleMap.entrySet()){

            batchCount = 0;
            tasks = new ArrayList<>();

            RocksIterator iterator = grabberRepository.readColumn(entry.getKey());
            iterator.seekToFirst();
            while (iterator.isValid()){ //|| batchCount >= 10){
                tasks.add(new SendTask(serverConfig.getUrlString(), iterator.key(), iterator.value(), entry.getKey(), this));
                iterator.next();
                batchCount++;
            }
            iterator.close();

            //execute
            tasks.stream()
                    .forEach(executorService::submit);

            //slow it down so that we get delete call while we are still iterating
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void startServer(){

        if(!keepRunning){
            keepRunning = true;
        }

        while (keepRunning){

            produce();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void shutDown(){
        keepRunning = false;
    }

    @Override
    public void close() throws IOException {
        shutDown();
        executorService.shutdown();
    }

    @Override
    public void onTaskCompletion(String identifier, byte[] key, boolean successful) {
        if(successful){
            grabberRepository.remove(identifier, key);
        }
    }
}
