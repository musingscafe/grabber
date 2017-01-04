![Build Status](https://travis-ci.org/musingscafe/grabber.svg?branch=master)

#Grabber
A data collector which allows application servers to push data to downstream servers without impacting application performance.
 
#Conventions
- Will - P0
- May - P1
- Grabber Client - GClient
- Grabber Server - GServer
- Application Server - Application, AServer
- Downstream Service and Storage - DService, DStorage
- Grabber Message - GrabberMessage/GMessage

##Grabber Message
GrabberMessage will have header and body, similar to HTTP. Header will be a dictionary/map e.g. `Map<String, String>`. Body will be serialized using Java byte serialization (in first version) and no codec support will be provided. Application can do it itself as body will accept byte[], String and a Serializable object.

AServer should be able to create a new GMessage and fill headers and body. 

```java
GrabberMessage message = new GrabberMessage();
message.addHeader(key, value);
```
  
GMessage must handle encoding. This is important to handle different locales. This is because client may use Grabber to persist actual data.
This can be required say for debugging or replaying a session. _**UTF-8** will be the default encoding.

GMessage's body should be a serializable object. i.e. for Java it must implement Serializable. **(Abstract)** As a simple POJO can be serialized into JSON by Jackson and then JSON String can be serialized into bytes. If we are providing serializer support then we can just try serializing the object and if it fails we can throw up.

##Grabber Client
- GClient **will** provide a easy to use API which will **loan the objects** to application code. 
- GClient **may** have to provide acknowledgment for saving the data.
- GClient **may** have to provide real time sync mechanism. That is no buffering on application server.
- GClient **may** have to provide callback/promises, this can be handled by application in start though.
- GClient **may** have to be configurable, so that it can handle different type of loads. **(Abstract)**
- GClients can be written in different languages and **will** support different platforms and thus the underlying data storage should have drivers for popular server side languages and must be available on different platforms.
- GClient **may** provide feature to send requests to AServer or DServer and not only to GServer. This will allow GClient to be used as a buffer/shock absorber for AServer and to communicate with other systems with retry. AServer should then provide sender object as a Consumer. **(Abstract)**

###Configuration
GClient will require GServer configuration. As there could be receivers other then GServer and different protocols may have to be supported, GClient will have a default implementation and a builder which can be used to customize GClient's behavior. 

e.g. GClient can receive a TCP or UDP based GSender. The connection will be maintained by AServer. 

###Components

####GClient Core

####GManager

####GSerializer

#####GStorage

####GCursor

####GSender
It should take a GRequest and GConnection and return a GResponse. 

####GRequest

####GResponse

####GConnection


###Basic Design
GClient will have a queue based storage. It will buffer the messages to be routed 


##Grabber Server


##Grabber Extensions
