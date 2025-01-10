package com.iebsa.knack.dataintegration.service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iebsa.knack.dataintegration.service.service.MapKnackToClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/knack")
public class KnackController {

    private final MapKnackToClient dataProcessor;

    // Inyección del servicio DataProcessor
    public KnackController(MapKnackToClient dataProcessor) {
        this.dataProcessor = dataProcessor;
    }

    /**
     * Endpoint para iniciar la carga inicial de registros desde Knack.
     *
     * @return Mensaje de éxito
     */
    @PostMapping("/initial-load")
    public ResponseEntity<String> initialLoad() throws JsonProcessingException {
        dataProcessor.initialLoad(); // Llama al método initialLoad
        return ResponseEntity.ok("Carga inicial iniciada correctamente.");
    }
}
