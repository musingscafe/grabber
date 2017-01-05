package com.musingscafe.grabber.core.registry;

public class Grabber {
    public void initialize() {
        ServiceLocator.getServiceLocator().register(ServiceRegistry.OBJECT_FACTORY, new ObjectFactory());
    }
}
