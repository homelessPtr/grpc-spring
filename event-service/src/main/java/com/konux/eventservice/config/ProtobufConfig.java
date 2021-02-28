package com.konux.eventservice.config;

import com.konux.eventservice.properties.ServiceProperties;
import com.konux.eventservice.proto.SenderGrpc;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ProtobufConfig {

    private final ServiceProperties properties;

    @Bean
    public Channel channelBuilder() {
        ServiceProperties.Service service = properties.getDbService();
        return ManagedChannelBuilder.forAddress(service.getHost(),
                service.getPort())
                .usePlaintext().build();
    }

    @Bean
    public SenderGrpc.SenderBlockingStub senderBlockingStub(Channel channel) {
        return  com.konux.eventservice.proto.SenderGrpc.newBlockingStub(channel);
    }

}
