package com.musingscafe.grabber.core

import java.util

import com.musingscafe.grabber.core.channel.{Channel, ChannelConfig}
import com.musingscafe.grabber.core.connectors.Connector
import com.musingscafe.grabber.core.consumers.Consumer
import com.musingscafe.grabber.core.registry.{ObjectFactory, ServiceLocator, ServiceRegistry}
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.{BeforeAndAfterEach, FunSpec, GivenWhenThen, Matchers}

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

class GrabberClientSpec extends FunSpec with Matchers with GivenWhenThen with BeforeAndAfterEach {

    describe("Grabber initialization") {
      it("GrabberClient.instance should always return same object") {
        Given("GrabberClient class")

        When("instance method is called multiple times")

        val first = GrabberClient.instance()
        val second = GrabberClient.instance()
        Then("same GrabberClient object should be returned")
        first should be(second)
      }

      it("GrabberClient.instance.open should set object factory") {
        Given("GrabberClient instance")

        val channelConfig1 = mockChannelConfig(1)
        val channelConfig2 = mockChannelConfig(2)
        val channel1 = mockChannel(channelConfig1)
        val channel2 = mockChannel(channelConfig2)
        val channels: util.List[Channel] = ArrayBuffer(channel1, channel2).asJava

        val objectFactoryBeforeSetup = ServiceLocator.getServiceLocator.get(ServiceRegistry.OBJECT_FACTORY, classOf[ObjectFactory])
        When("open is called on it with valid parameters")

        Then("it should setup ObjectFactory")
        GrabberClient.instance().open(channels, "dbpath")
        val objectFactory = ServiceLocator.getServiceLocator.get(ServiceRegistry.OBJECT_FACTORY, classOf[ObjectFactory])
        objectFactoryBeforeSetup should be(null)
        objectFactory should not be(null)
      }

      it("GrabberClient.instance.open should set repository and producer to channels") {
        Given("GrabberClient instance")

        val channelConfig1 = mockChannelConfig(1)
        val channelConfig2 = mockChannelConfig(2)
        val channel1 = mockChannel(channelConfig1)
        val channel2 = mockChannel(channelConfig2)
        val channels: util.List[Channel] = ArrayBuffer(channel1, channel2).asJava

        When("open is called on it with valid parameters")

        Then("it should setup repository and producer to channels")
        GrabberClient.instance().open(channels, "dbpath")
        channel1.getChannelConfig.getGrabberRepository should not be(null)
        channel1.getChannelConfig.getProducer should not be(null)
      }
    }

    def mockChannelConfig (int: Int) : ChannelConfig = {

      val consumer = EasyMockSugar.mock[Consumer]
      val consumers: util.List[Consumer] = ArrayBuffer(consumer).asJava
      val connector = EasyMockSugar.mock[Connector]
      val channelConfig = new ChannelConfig(String.valueOf(int), consumers, connector, false)
      channelConfig
    }

    def mockChannel (config: ChannelConfig) : Channel = {
      val channel = new Channel(config)
      channel
    }
}
