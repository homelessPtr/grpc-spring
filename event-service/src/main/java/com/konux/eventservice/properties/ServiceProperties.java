package com.konux.eventservice.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "service")
public class ServiceProperties {

    private Service dbService;

    @Data
    public static class Service {

        private String host;
        private Integer port;
    }
}
