package com.musingscafe.grabber.core.registry;

import com.musingscafe.grabber.core.DefaultSerializer;
import com.musingscafe.grabber.core.GrabberRepository;
import com.musingscafe.grabber.core.Serializer;
import com.musingscafe.grabber.core.channel.ChannelConfig;
import com.musingscafe.grabber.core.Producer;
import com.musingscafe.grabber.core.RocksDbProducer;

import java.util.List;

/**
 * Created by ayadav on 1/4/17.
 */
public class ObjectFactory {
    public GrabberRepository getRepository(String databasePath,
                                           List<ChannelConfig> channelConfigs,
                                           Serializer serializer) {
        GrabberRepository grabberRepository = ServiceLocator.getServiceLocator()
                                                .get(ServiceRegistry.GRABBER_REPOSITORY, GrabberRepository.class);
        if (grabberRepository == null) {
            grabberRepository = new GrabberRepository(databasePath, channelConfigs, serializer);
            ServiceLocator.getServiceLocator().register(ServiceRegistry.GRABBER_REPOSITORY, grabberRepository);
        }

        return grabberRepository;
    }

    public Serializer getDefaultSerializer() {
        Serializer serializer = ServiceLocator.getServiceLocator()
                .get(ServiceRegistry.DEFAULT_SERIALIZER, DefaultSerializer.class);
        if (serializer == null) {
            serializer = new DefaultSerializer();
            ServiceLocator.getServiceLocator().register(ServiceRegistry.DEFAULT_SERIALIZER, serializer);
        }

        return serializer;
    }

    public Producer getRocksDbProducer(Serializer serializer, GrabberRepository grabberRepository) {
        Producer producer = ServiceLocator.getServiceLocator()
                .get(ServiceRegistry.ROCKS_DB_PRODUCER, RocksDbProducer.class);
        if (producer == null) {
            producer = new RocksDbProducer(serializer, grabberRepository);
            ServiceLocator.getServiceLocator().register(ServiceRegistry.ROCKS_DB_PRODUCER, producer);
        }

        return producer;
    }
}
