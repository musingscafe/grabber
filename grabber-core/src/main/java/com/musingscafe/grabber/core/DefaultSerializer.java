package com.musingscafe.grabber.core;

import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by ayadav on 1/4/17.
 */
@Component
public class DefaultSerializer implements Serializer{
    private static final byte SERIALIZATION_MODE_DEFAULT            = 0;
    private static final byte SERIALIZATION_MODE_JAVA               = 1;
    private static final byte SERIALIZATION_MODE_NONE               = 2;
    private static final byte JAVA_BYTE_PREFIX                      = 1;
    private static final byte NONE_BYTE_PREFIX                      = 0;

    @Override
    public byte[] serialize(Serializable serializable){
        if(serializable instanceof byte[]){
            return convertToBytes(serializable, SERIALIZATION_MODE_JAVA);
        }

        return convertToBytes(serializable, SERIALIZATION_MODE_NONE);
    }
    private byte[] convertToBytes(Serializable serializable, int serializationMode) {
        if(serializable == null){
            return null;
        }

        byte[] bytes = bytesWithPrefix(objectToBytes(serializable), serializationMode);
        return bytes;
    }

    private byte[] bytesWithPrefix(byte[] bytes, int serializationMode){
        byte[] prefixedBytes;
        switch (serializationMode){
            case SERIALIZATION_MODE_DEFAULT:
            case SERIALIZATION_MODE_JAVA:
                prefixedBytes = addPrefix(bytes, JAVA_BYTE_PREFIX);
                break;
            case SERIALIZATION_MODE_NONE:
                prefixedBytes = addPrefix(bytes, NONE_BYTE_PREFIX);
                break;
            default:
                prefixedBytes = addPrefix(bytes, SERIALIZATION_MODE_NONE);
                break;
        }

        return prefixedBytes;
    }

    private byte[] addPrefix(byte[] bytes, byte prefix){
        byte[] prefixedBytes = new byte[bytes.length + 1];
        prefixedBytes[0] = prefix;
        System.arraycopy(bytes, 0, prefixedBytes, 1, bytes.length);
        return prefixedBytes;
    }

    private byte[] objectToBytes(Serializable serializable){
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput objectOutput = null;
        try{
            objectOutput = new ObjectOutputStream(byteArrayOutputStream);
            objectOutput.writeObject(serializable);
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try{
                if(objectOutput != null){
                    objectOutput.close();
                }
            }
            catch(Exception e){

            }
            try{
                if(byteArrayOutputStream != null){
                    byteArrayOutputStream.close();
                }
            }
            catch(Exception e){

            }
        }

        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes){
        if (null == bytes)
        {
            return null;
        }
        byte serializationMode = bytes[0];
        Serializable	serializable;
        switch (serializationMode)
        {
            default:
            case SERIALIZATION_MODE_JAVA:
                serializable = (Serializable) convertToObject(removePrefix(bytes));
                break;

            case SERIALIZATION_MODE_NONE:
                serializable = (Serializable) removePrefix(bytes);

        }
        return serializable;
    }

    private byte[] removePrefix(byte[] bytes)
    {
        byte[] withoutPrefix = new byte[bytes.length - 1];
        System.arraycopy(bytes, 1, withoutPrefix, 0, bytes.length - 1);
        return withoutPrefix;
    }

    private Object convertToObject(byte[] value) {
        if(value == null){
            return null;
        }
        byte[] bytes = value;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInput objectInputStream = null;
        Object object = null;

        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            object = objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(objectInputStream != null){
                    objectInputStream.close();
                }
            } catch (IOException e) {

            }

            try{
                if(byteArrayInputStream != null){
                    byteArrayInputStream.close();
                }
            }catch (Exception e){

            }
        }


        return object;
    }
}