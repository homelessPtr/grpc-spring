package com.konux.dbservice.config

import com.konux.dbservice.repo.EventRepo
import com.konux.dbservice.service.EventService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventServiceConfig {

    @Bean
    fun eventService(repo : EventRepo) : EventService =
            EventService(repo)

}