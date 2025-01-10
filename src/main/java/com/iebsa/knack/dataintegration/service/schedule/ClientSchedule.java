package com.iebsa.knack.dataintegration.service.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iebsa.knack.dataintegration.service.service.MapKnackToClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClientSchedule {

    @Autowired
    private MapKnackToClient mapKnackToClient;

    @Scheduled(cron = "0 0 * * * *")
    public void refreshAuthToken() throws JsonProcessingException {
        mapKnackToClient.schedulelLoad();
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void executeEveryFiveMinutes() {
        System.out.println("Tarea ejecutada cada 5 minutos - " + java.time.LocalDateTime.now());
    }
}
