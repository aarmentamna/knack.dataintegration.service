package com.iebsa.knack.dataintegration.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iebsa.knack.dataintegration.service.config.KnackClient;
import com.iebsa.knack.dataintegration.service.entity.Client;
import com.iebsa.knack.dataintegration.service.model.KnackRecord;
import com.iebsa.knack.dataintegration.service.model.KnackResponse;
import com.iebsa.knack.dataintegration.service.repository.ClientRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MapKnackToClient {

    private final KnackClient knackClient;
    private final ClientRepository recordRepository;

    public MapKnackToClient(KnackClient knackClient, ClientRepository recordRepository) {
        this.knackClient = knackClient;
        this.recordRepository = recordRepository;
    }

    public void initialLoad() throws JsonProcessingException {
        try {
            Mono<String> recordsClients = knackClient.fetchRecords();
            String jsonResponse = recordsClients.block();
            ObjectMapper objectMapper = new ObjectMapper();
            KnackResponse response = objectMapper.readValue(jsonResponse, KnackResponse.class);
            List<Client> clients = response.getRecords().stream()
                    .map(this::mapKnackToClient)
                    .toList();
            recordRepository.saveAll(clients);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void schedulelLoad() throws JsonProcessingException {
        try {
            Mono<String> recordsClients = knackClient.fetchRecords();
            String jsonResponse = recordsClients.block();
            ObjectMapper objectMapper = new ObjectMapper();
            KnackResponse response = objectMapper.readValue(jsonResponse, KnackResponse.class);
            List<Client> clients = response.getRecords().stream()
                    .map(this::mapKnackToClient)
                    .toList();
            recordRepository.saveAll(clients);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Client mapKnackToClient(KnackRecord record) {
        Client client = new Client();

        // Validar y mapear campos básicos
        client.setFirstName(Optional.ofNullable(record.getField_1().split(" ")[0]).orElse("Unknown"));
        client.setLastName(Optional.ofNullable(record.getField_1().split(" ").length > 1 ? record.getField_1().split(" ")[1] : "").orElse("Unknown")); // Usando el mismo campo como ejemplo
        client.setIndustry(Optional.ofNullable(record.getField_2()).orElse("Unknown"));

        // Validar y mapear campos de dirección
        if (record.getField_3_raw() != null) {
            client.setStreetAddress(Optional.ofNullable(record.getField_3_raw().getStreet()).orElse("Unknown"));
            client.setStreetAddress2(Optional.ofNullable(record.getField_3_raw().getStreet2()).orElse(null));
            client.setCity(Optional.ofNullable(record.getField_3_raw().getCity()).orElse("Unknown"));
            client.setState(Optional.ofNullable(record.getField_3_raw().getState()).orElse("Unknown"));
            client.setZipCode(Optional.ofNullable(record.getField_3_raw().getZip()).orElse("00000"));
        } else {
            // Valores predeterminados si field_3_raw es null
            client.setStreetAddress("Unknown");
            client.setStreetAddress2(null);
            client.setCity("Unknown");
            client.setState("Unknown");
            client.setZipCode("00000");
        }

        // Validar y mapear el número de teléfono
        client.setPhoneNumber(Optional.ofNullable(record.getField_4()).map(this::cleanPhone).orElse("N/A"));

        // Validar y mapear el correo electrónico
        client.setEmail(Optional.ofNullable(record.getField_5()).map(this::cleanEmail).orElse("unknown@example.com"));

        // Validar y mapear el estado (enum)
        client.setStatus(
                Optional.ofNullable(record.getField_6())
                        .flatMap(this::mapStatusSafe)
                        .orElse(Client.Status.Pending)
        );

        // Validar y mapear el valor total del contrato
        client.setTotalContractValue(
                Optional.ofNullable(record.getField_43())
                        .map(this::parseAmount)
                        .orElse(BigDecimal.ZERO)
        );
        client.setCreatedDate(Optional.ofNullable(record.getField_56().replace("/", "-"))
                .filter(field -> !field.isEmpty()) // Filter out empty strings
                .map(LocalDate::parse) // Parse only if non-empty
                .orElse(LocalDate.now()));
        return client;
    }

    private String cleanPhone(String field4) {

        return field4.replaceAll("<.*?>", "").replace("tel:", "").trim();
    }

    private String cleanEmail(String field5) {

        return field5.replaceAll("<.*?>", "").replace("mailto:", "").trim();
    }

    private Optional<Client.Status> mapStatusSafe(String status) {
        try {
            // Intenta convertir el string al valor correspondiente del Enum
            return Optional.of(Client.Status.valueOf(status));
        } catch (IllegalArgumentException | NullPointerException e) {
            // Retorna un Optional vacío si el string no coincide o es null
            return Optional.empty();
        }
    }

    private BigDecimal parseAmount(String field43) {
        try {
            return new BigDecimal(field43.replaceAll("[^\\d.]", ""));
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }


}
