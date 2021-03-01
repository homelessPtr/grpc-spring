package com.konux.dbservice.property

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "grpc-server")
data class ServerProperties(val server: Server) {
    data class Server(val port: Int?)
}