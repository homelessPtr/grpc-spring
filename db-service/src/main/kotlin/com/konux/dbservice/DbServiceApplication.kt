package com.konux.dbservice

import com.konux.dbservice.property.ServerProperties
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableConfigurationProperties(ServerProperties::class)
@EnableJpaRepositories
class DbServiceApplication

fun main(args: Array<String>) {
    runApplication<DbServiceApplication>(*args)

}
