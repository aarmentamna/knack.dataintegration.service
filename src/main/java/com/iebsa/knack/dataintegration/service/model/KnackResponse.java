package com.iebsa.knack.dataintegration.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KnackResponse {
    private int total_pages;
    private int current_page;
    private int total_records;
    private List<KnackRecord> records; // Aquí mapeamos los registros

}
