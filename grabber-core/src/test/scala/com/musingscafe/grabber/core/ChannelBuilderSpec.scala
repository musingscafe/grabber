package com.musingscafe.grabber.core

import java.util

import com.musingscafe.grabber.core.channel.ChannelBuilder
import com.musingscafe.grabber.core.connectors.Connector
import com.musingscafe.grabber.core.consumers.Consumer
import org.scalatest.easymock.EasyMockSugar
import org.scalatest.{BeforeAndAfterEach, FunSpec, GivenWhenThen, Matchers}

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

class ChannelBuilderSpec extends FunSpec with Matchers with GivenWhenThen with BeforeAndAfterEach {

    describe("an instance of ChannelBuilder") {
      it("should construct Channel with provided parameters") {
        Given("valid parameters are provided")
        val channelBuilder = new ChannelBuilder
        val channelIdentifier = "default"
        val consumer1 = EasyMockSugar.mock[Consumer]
        val consumer2 = EasyMockSugar.mock[Consumer]
        val connector = EasyMockSugar.mock[Connector]
        val consumers: util.List[Consumer] = ArrayBuffer(consumer1, consumer2).asJava

        When("build is called")
        val channel = channelBuilder.setChannelIdentifier(channelIdentifier)
          .setConnector(connector).setConsumers(consumers).setShouldExecuteSelf(true).build()

        Then("valid Channel is returned")
        channel should not be(null)
        channel.getChannelConfig should not be(null)
        channel.getChannelConfig.getChannelIdentifier should be(channelIdentifier)
      }
    }
}
