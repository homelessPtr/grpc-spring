package com.konux.dbservice.grpcserver

import com.konux.dbservice.property.ServerProperties
import com.konux.dbservice.service.EventService
import io.grpc.Server
import io.grpc.ServerBuilder
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


@Component
class GRpcEventServer(properties: ServerProperties,
                      private val eventService: EventService) {
    private val server: Server
    private val port :Int

    init {
        port = properties.server.port ?: DEFAULT_PORT
        server = ServerBuilder.forPort(port).addService(eventService).build()
    }

    @PostConstruct
    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
                Thread {
                    println("shutting down server")
                    this@GRpcEventServer.stop()
                    println("server shut down")
                }
        )
    }
    @PreDestroy
    fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    companion object {
        const val DEFAULT_PORT = 8080
    }
}