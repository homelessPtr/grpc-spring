package com.konux.eventservice.service;

import com.konux.eventservice.controller.model.BaseResponse;
import com.konux.eventservice.controller.model.Event;
import com.konux.eventservice.exception.RpcException;
import com.konux.eventservice.proto.EventProto;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SenderService implements ISenderService {

    private final com.konux.eventservice.proto.SenderGrpc.SenderBlockingStub blockingStub;

    @Override
    public BaseResponse send(Event event) {
        com.konux.eventservice.proto.BaseResponse response;
        com.konux.eventservice.proto.Event req = com.konux.eventservice.proto.Event.newBuilder()
                .setTimestamp(event.getTimestamp())
                .setUserId(event.getUserId())
                .setEvent(event.getEvent()).build();
        try{
            response = blockingStub.send(req);
        } catch (StatusRuntimeException e) {
            log.error( "RPC failed: {}", e.getStatus());
            throw new RpcException(e.getStatus().getDescription());
        }
        return convert(response);
    }

    private BaseResponse convert(com.konux.eventservice.proto.BaseResponse response) {
        if (response != null)
        return BaseResponse.builder().result(response.getResult())
                .note(response.getNote()).build();
        else throw new RpcException("Response is null");
    }

}
