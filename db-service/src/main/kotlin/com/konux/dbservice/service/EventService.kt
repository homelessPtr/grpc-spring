package com.konux.dbservice.service

import com.konux.dbservice.domainobject.EventDO
import com.konux.dbservice.repo.EventRepo
import com.konux.proto.BaseResponse
import com.konux.proto.Event
import com.konux.proto.SenderGrpc

import io.grpc.stub.StreamObserver
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Slf4j
@Component
class EventService(private val eventRepo: EventRepo) : SenderGrpc.SenderImplBase() {

    val logger : Logger = LoggerFactory.getLogger(EventService::class.java)

    @Transactional
    override fun send(request: Event?, responseObserver: StreamObserver<BaseResponse>?) {
        val baseResponse: BaseResponse?
        if (request != null) {
            baseResponse = try {
                logger.info("Event: {}", request)
                val event = eventRepo.save(EventDO(
                        timestamp = request.timestamp,
                        user = request.userId,
                        event = request.event))
                buildSuccessResponse(event.id)
            } catch (e: Exception) {
                buildErrorResponse(e)
            }
            responseObserver?.onNext(baseResponse)
            responseObserver?.onCompleted()
        }
    }

    private fun buildSuccessResponse(id: Long?): BaseResponse =
            BaseResponse.newBuilder()
                    .setResult(SUCCESS)
                    .setNote(String.format("Event with id = %s created", id.toString())).build()

    private fun buildErrorResponse(exception: Exception): BaseResponse =
            BaseResponse.newBuilder()
                    .setResult(ERROR)
                    .setNote(exception.message).build()

    companion object {
        const val SUCCESS = "SUCCESS"
        const val ERROR = "ERROR"
    }
}
