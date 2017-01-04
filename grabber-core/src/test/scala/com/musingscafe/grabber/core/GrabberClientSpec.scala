package com.musingscafe.grabber.core

import org.scalatest.{BeforeAndAfterEach, FunSpec, GivenWhenThen, Matchers}

import scala.collection.JavaConverters._

class GrabberClientSpec extends FunSpec with Matchers with GivenWhenThen with BeforeAndAfterEach{

  describe("GrabberClient Instance") {

    it ("should create instance of Grabber Client if both parameters are present") {
      Given("both arguments provided")
      val dbPath = "db.path"
      val stores: List[String] = List("messageStore")
      When("Constructor is called")
      val grabberClient = new GrabberClient(dbPath, stores.asJava)

      Then("Valid instace of Grabber Client should return")
      grabberClient should not be null
      grabberClient.getDbPath should be("db.path")
      grabberClient.getStores.size() should be(1)
    }

    it ("should throw IllegalArgumentException while creating Grabber Client if any parameter is null") {
      Given("one argument is null")
      val dbPath = null
      val stores: List[String] = List("messageStore")
      When("Constructor is called")
      Then("IllegalArgumentException is thrown")
      assertThrows[IllegalArgumentException] {
        val grabberClient = new GrabberClient(dbPath, stores.asJava)
      }
    }
  }

}
