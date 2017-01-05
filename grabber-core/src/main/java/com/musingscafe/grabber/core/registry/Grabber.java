package com.musingscafe.grabber.core.registry;

public class Grabber {
    public static void initialize() {
        ServiceLocator.getServiceLocator().register(ServiceRegistry.OBJECT_FACTORY, new ObjectFactory());
    }
}
