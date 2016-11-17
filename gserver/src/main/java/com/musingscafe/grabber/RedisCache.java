package com.musingscafe.grabber;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.io.Serializable;

/**
 * Created by ayadav on 11/17/16.
 */
public class RedisCache implements ICache{

    //address of your redis server
    private static final String redisHost = "192.168.99.100";
    private static final Integer redisPort = 8081;
    //the jedis connection pool..
    private static JedisPool pool = null;

    public RedisCache() {
        //configure our pool connection
        pool = new JedisPool(redisHost, redisPort);
    }

    private byte[] convertToBytes(Object o){
        if(!(o instanceof Serializable)){
            throw new IllegalArgumentException("Serializable expected");
        }

        return RSerializer.toByteArray((Serializable) o);
    }

    public void puts(Object key, Object value){
        getClient().set(key.toString().getBytes(), value.toString().getBytes());
    }

    @Override
    public Object get(Object key) {
        return RSerializer.toObject(getClient().get(convertToBytes(key)));
    }

    //Implementation needs change
    @Override
    public Object getExisting(Object key) throws Exception {
        Object value = get(key);

        if(value == null){
            throw new Exception("When you have not saved it, how can you expect it to be here.");
        }
        return value;
    }

    @Override
    public void put(Object key, Object value) {
        Jedis jedis = getClient();
        Transaction transaction = jedis.multi();
        Response<byte[]> oldVal = transaction.get(convertToBytes(key));
        transaction.set(convertToBytes(key), convertToBytes(value));
        transaction.exec();
    }

    @Override
    public void hardFlush() {
        Jedis jedis = getClient();
        jedis.flushAll();
    }

    @Override
    public boolean remove(Object key) {
        if(!(key instanceof Serializable)){
            throw new IllegalArgumentException("Serializable expected");
        }

        Jedis jedis = getClient();
        Transaction transaction = jedis.multi();
        Response<byte[]> val = transaction.get(convertToBytes(key));
        Response<Long> result = transaction.del(convertToBytes(key));
        transaction.exec();

        if(result.get() == 0){
            return false;
        }
        return true;
    }

    @Override
    public void softFlush() {

    }

    private Jedis getClient(){
        Jedis jedis = pool.getResource();
        return jedis;
    }

}
