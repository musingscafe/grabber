package com.musingscafe.grabber.core.registery;

public class BootStrap {
    public void register() {
        ServiceLocator.getServiceLocator().register(ServiceRegistry.OBJECT_FACTORY, new ObjectFactory());
    }
}
