package com.musingscafe.grabber.core

import org.scalatest.{BeforeAndAfterEach, FunSpec, GivenWhenThen, Matchers}

import java.util
import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

class GrabberClientBuilderSpec extends FunSpec with Matchers with GivenWhenThen with BeforeAndAfterEach
{
  describe("GrabberClientBuilder instance") {
    it("should construct GrabberClient correctly with default parameters") {
      Given("no parameters are set")
      val grabberClientBuilder: GrabberClientBuilder = new GrabberClientBuilder

      When("builder.build is called")
      val grabberClient = grabberClientBuilder.build()

      Then("grabber client should be returned")
      grabberClient should not be null
      grabberClient.getDbPath should be("grabber.db")
      grabberClient.getStores.size() should be(1)

    }

    it("should construct GrabberClient correctly with provided parameters") {
      Given("valid parameters are set")
      val grabberClientBuilder: GrabberClientBuilder = new GrabberClientBuilder
      val stores: util.List[String] = ArrayBuffer("store").asJava
      When("builder.build is called")
      val grabberClient = grabberClientBuilder.withDBPath("dbpath").withStores(stores).build()

      Then("grabber client should be returned")
      grabberClient should not be null
      grabberClient.getDbPath should be("dbpath")
      val returnedStores = grabberClient.getStores
      returnedStores.size() should be(1)
      returnedStores.get(0) should be("store")

    }
  }

}

