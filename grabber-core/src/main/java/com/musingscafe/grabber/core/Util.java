package com.musingscafe.grabber.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by ayadav on 11/16/16.
 */
public class Util {
    /**
     * Serialize any object
     * @param obj
     * @return
     */
    public static String serialize(Object obj) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(obj);
            so.flush();
            // This encoding induces a bijection between byte[] and String (unlike UTF-8)
            return bo.toString("ISO-8859-1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * Deserialize any object
     * @param str
     * @param cls
     * @return
     */
    public static <T> T deserialize(String str, Class<T> cls) {
        // deserialize the object
        try {
            // This encoding induces a bijection between byte[] and String (unlike UTF-8)
            byte b[] = str.getBytes("ISO-8859-1");
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            return cls.cast(si.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
