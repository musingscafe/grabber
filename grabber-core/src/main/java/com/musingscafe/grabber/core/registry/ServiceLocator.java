package com.musingscafe.grabber.core.registry;

import java.util.HashMap;
import java.util.Map;

public class ServiceLocator {
    private static ServiceLocator serviceLocator = new ServiceLocator();
    private final Map<String, Object> objectMap = new HashMap<>();

    private ServiceLocator(){
        ServiceLocator.getServiceLocator().register(ServiceRegistry.OBJECT_FACTORY, new ObjectFactory());
    }

    public void register(String key, Object object){
        objectMap.put(key, object);
    }

    public <T> T get(String key, Class<T> clazz){
        if(!objectMap.containsKey(key)){
            return null;
        }
        return clazz.cast(objectMap.get(key));
    }

    public static ServiceLocator getServiceLocator(){
        return serviceLocator;
    }

}
