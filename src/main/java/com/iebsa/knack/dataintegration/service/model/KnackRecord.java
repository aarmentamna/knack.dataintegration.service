package com.iebsa.knack.dataintegration.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class KnackRecord {
    private String id;
    private String field_1;
    private String field_2;
    private Field3Raw field_3_raw;
    private String field_4;
    private String field_5;
    private String field_6;
    private String field_43;
    private String field_56;

    @JsonSetter("field_3_raw")
    public void setField_3_raw(Object field_3_raw) {
        if (field_3_raw instanceof String && ((String) field_3_raw).isEmpty()) {
            this.field_3_raw = null; // Si es una cadena vacía, asigna null
        } else if (field_3_raw instanceof Map) {
            ObjectMapper mapper = new ObjectMapper();
            this.field_3_raw = mapper.convertValue(field_3_raw, Field3Raw.class); // Convierte el objeto JSON en Field3Raw
        }
    }
}
