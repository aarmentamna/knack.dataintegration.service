package com.iebsa.knack.dataintegration.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true) // Ignora propiedades desconocidas en el JSON
public class Field3Raw {
    private String street;
    private String street2;
    private String city;
    private String state;
    private String zip;
}
