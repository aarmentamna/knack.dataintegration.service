package com.iebsa.knack.dataintegration.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.knack.com/v1") // Configura la base URL
                .defaultHeader("X-Knack-Application-ID", "674f6ea6e31ff5028042c9a6")
                .defaultHeader("X-Knack-REST-API-Key", "7f789992-45c8-4d62-98a8-0563304786e1")
                .defaultHeader("Content-Type", "application/json") // Configura el Content-Type
                .build();
    }
}
