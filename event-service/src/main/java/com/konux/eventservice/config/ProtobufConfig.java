package com.konux.eventservice.config;

import com.google.protobuf.BlockingRpcChannel;
import com.konux.eventservice.properties.ServiceProperties;
import com.konux.eventservice.proto.EventProto;
import com.konux.eventservice.proto.SenderGrpc;
import com.konux.eventservice.service.ISenderService;
import com.konux.eventservice.service.SenderService;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProtobufConfig {

    private final ServiceProperties properties;

    @Bean
    public Channel channelBuilder() {
        return ManagedChannelBuilder.forAddress(properties.getDbService().getHost(),
                properties.getDbService().getPort()).usePlaintext().build();
    }

    @Bean
    public SenderGrpc.SenderBlockingStub senderBlockingStub(Channel channel) {
        return  com.konux.eventservice.proto.SenderGrpc.newBlockingStub(channel);
    }

    @Bean
    public ISenderService senderService(SenderGrpc.SenderBlockingStub blockingStub) {
        return new SenderService(blockingStub);
    }
}
