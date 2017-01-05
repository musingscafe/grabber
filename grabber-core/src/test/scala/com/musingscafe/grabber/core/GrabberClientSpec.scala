package com.musingscafe.grabber.core

import org.scalatest.{BeforeAndAfterEach, FunSpec, GivenWhenThen, Matchers}

/**
  * Created by ayadav on 1/5/17.
  */
class GrabberClientSpec extends FunSpec with Matchers with GivenWhenThen with BeforeAndAfterEach {

    describe("only one instance of GrabberClient") {
      it("GrabberClient.instance should always return same object") {
        Given("GrabberClient class")

        When("instance method is called multiple times")
        val first = GrabberClient.instance()
        val second = GrabberClient.instance()

        Then("same GrabberClient object should be returned")
        first should be(second)
      }

      it("GrabberClient.instance.open should set repository and producer to channels") {

      }
    }
}
