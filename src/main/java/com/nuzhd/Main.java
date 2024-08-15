package com.nuzhd;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuzhd.model.Ticket;
import com.nuzhd.service.TicketService;
import com.nuzhd.util.JsonParser;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        JsonParser parser = new JsonParser();
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);

        String ticketsJson = parser.parseJson(args[0]).toString();
        Ticket[] tickets = mapper.readValue(ticketsJson, Ticket[].class);

        TicketService ticketService = new TicketService();

        System.out.println("Минимальные длительности полетов для каждого перевозчика: ");
        Map<String, Duration> minFlightTimes = ticketService.getMinFlightTimes(tickets);

        for (Map.Entry<String, Duration> entry : minFlightTimes.entrySet()) {
            System.out.printf("Компания %s : %d час(ов) %d минут(ы)%n",
                    entry.getKey(),
                    entry.getValue().toHoursPart(),
                    entry.getValue().toMinutesPart()
            );
        }

        System.out.printf(
                "Разница между средней и медианной ценой: %d%n",
                ticketService.getAvgPrice(tickets).subtract(ticketService.getMedianPrice(tickets)).intValue()
        );
    }
}