package com.konux.eventservice;

import com.konux.proto.BaseResponse;
import com.konux.proto.Event;
import com.konux.proto.SenderGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EventServiceApplicationTests {

     Server server;
     ManagedChannel channel;
     SenderGrpc.SenderBlockingStub blockingStub;
     Event sampleEvent = Event.newBuilder()
            .setTimestamp(123)
            .setUserId(123)
            .setEvent("Test event").build();


     class MockService extends SenderGrpc.SenderImplBase {
        @Override
        public void send(Event request, StreamObserver<BaseResponse> responseObserver) {
            assertEquals(request.getTimestamp(), sampleEvent.getTimestamp());
            assertEquals(request.getEvent(), sampleEvent.getEvent());
            assertEquals(request.getUserId(), sampleEvent.getTimestamp());
            System.out.println("Event " + request.getEvent());
            BaseResponse stat = BaseResponse.newBuilder()
                    .setResult("SUCCESS")
                    .setNote("Event received")
                    .build();
            responseObserver.onNext(stat);
            responseObserver.onCompleted();
        }
    }

    @BeforeEach
    public void startServer() throws IOException {
        server = ServerBuilder.forPort(8085).addService(new MockService()).build().start();
        int port = server.getPort();
        channel = ManagedChannelBuilder.forAddress("localhost", port)
                .usePlaintext()
                .directExecutor()
                .build();
        blockingStub = SenderGrpc.newBlockingStub(channel);
    }

    @Test
    public void testUnary() throws IOException {
        Event event = Event.newBuilder()
                .setEvent(sampleEvent.getEvent())
                .setUserId(sampleEvent.getUserId())
                .setTimestamp(sampleEvent.getTimestamp())
                .build();
        BaseResponse response = blockingStub.send(event);
        assertEquals(response.getResult(), "SUCCESS");
        assertEquals(response.getNote(), "Event received");
        System.out.println("Received response from service: " + response);
    }

}

