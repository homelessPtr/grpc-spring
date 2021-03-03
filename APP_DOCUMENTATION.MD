## Implementation Details

There are two applications: 
- event-service, written in Java. To start application run
    java -jar event-service-0.0.1-SNAPSHOT.jar
- db-service, written in Kotlin. To start application run
    java -jar db-service-0.0.1-SNAPSHOT.jar 
  
I used 11 java version. 
  
##event-service

It's a Spring Boot Java application with REST-service which consumes application/json. 
To send an event you should call POST using on url  http://localhost:8080/core/event-service/event
while application is running. Also you can use swagger to call application from your browser.
To do so [click here](http://localhost:8080/core/event-service/swagger-ui.html)
To interact with db-service application uses gRPC protocol, all requests serializing 
in Google Protocol Buffers format.

##db-service

It's also a Spring Boot application, but it's written in Kotlin. It runs an gRPC server
on port 8081 and consumes all events from event-service to store it in H2 database and
logfile, which could be found under appLogs/ folder. To access database [follow link](http://localhost:8082/h2)
and login with user/pass admin/nimda123. You can see all events stored in db. 
I've just thought that it's better than just logging them to file :)

##proto

Proto module is for common dependencies and generated files. It's provided as
a dependency for both applications.

##Building applications

To build both applications just run mvn clean install on the root pom.xml 

##Note

It's my first experience with both gRPC and Kotlin, and I find it very
exciting. So any feedback you can provide is really appreciated :)