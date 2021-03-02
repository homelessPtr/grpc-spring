package com.konux.eventservice.service;

import com.konux.eventservice.controller.model.BaseResponse;
import com.konux.eventservice.controller.model.Event;
import com.konux.eventservice.exception.RpcException;
import com.konux.proto.SenderGrpc;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SenderService implements ISenderService {

    private final SenderGrpc.SenderBlockingStub blockingStub;

    @Override
    public BaseResponse send(Event event) {
        com.konux.proto.BaseResponse response;
        com.konux.proto.Event req = com.konux.proto.Event.newBuilder()
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

    private BaseResponse convert(com.konux.proto.BaseResponse response) {
        if (response != null)
        return BaseResponse.builder().result(response.getResult())
                .note(response.getNote()).build();
        else throw new RpcException("Response is null");
    }

}
