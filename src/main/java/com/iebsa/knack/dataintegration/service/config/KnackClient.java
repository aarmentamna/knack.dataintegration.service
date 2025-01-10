package com.iebsa.knack.dataintegration.service.config;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatus;

import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

@Service
public class KnackClient {

    private final WebClient webClient;

    public KnackClient(WebClient webClient) {
        this.webClient = webClient;
    }


    public Mono<String> fetchRecords() {
        return webClient.get()
                .uri("/objects/object_1/records")
                .retrieve()
                .onStatus(statusCode -> {
                    HttpStatus status = HttpStatus.resolve(statusCode.value());
                    return status != null && status.isError(); // Verifica si el estado es un error
                }, response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                System.err.println("Error del servidor: " + errorBody);
                                return Mono.error(new RuntimeException("Error HTTP: " + response.statusCode()));
                            });
                })
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Respuesta recibida: " + response))
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }

    public Mono<String> ScheduleRecords() {
        String requestBody = String.format(
                "{\"filters\": [{\"field\": \"field_56\", \"operator\": \"is\", \"value\": \"%s\"}]}",
                LocalDate.now()
        );
        return webClient.post()
                .uri("/objects/object_1/records")
                .bodyValue(requestBody)
                .retrieve()
                .onStatus(statusCode -> {
                    HttpStatus status = HttpStatus.resolve(statusCode.value());
                    return status != null && status.isError(); // Verifica si el estado es un error
                }, response -> {
                    return response.bodyToMono(String.class)
                            .flatMap(errorBody -> {
                                System.err.println("Error del servidor: " + errorBody);
                                return Mono.error(new RuntimeException("Error HTTP: " + response.statusCode()));
                            });
                })
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Respuesta recibida: " + response))
                .doOnError(error -> System.err.println("Error: " + error.getMessage()));
    }



}
