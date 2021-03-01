package com.konux.dbservice.config

import com.konux.dbservice.repo.EventRepo
import com.konux.dbservice.service.EventService
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventServiceConfig {

    @Bean
    @ConditionalOnMissingBean
    fun eventService(repo : EventRepo) : EventService =
            EventService(repo)

}