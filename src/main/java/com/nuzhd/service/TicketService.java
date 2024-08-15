package com.nuzhd.service;

import com.nuzhd.model.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class TicketService {

    private Set<String> getCarriers(List<Ticket> tickets) {
        return tickets.stream()
                .map(Ticket::carrier)
                .collect(Collectors.toSet());
    }

    public Map<String, Duration> getMinFlightTimes(List<Ticket> tickets) {
        Set<String> carriers = getCarriers(tickets);

        Map<String, Duration> result = new HashMap<>();
        for (String carrier : carriers) {
            result.put(carrier,
                    tickets.stream()
                            .filter(t -> t.carrier().equals(carrier))
                            .map(Ticket::getFlightDuration)
                            .min(Comparator.naturalOrder())
                            .get()
            );
        }
        return result;
    }

    public BigDecimal getAvgPrice(List<Ticket> tickets) {
        BigDecimal result = BigDecimal.ZERO;

        for (Ticket t : tickets) {
            result = result.add(t.price());
        }

        return result.divide(BigDecimal.valueOf(tickets.size()), RoundingMode.HALF_EVEN);
    }

    public BigDecimal getMedianPrice(List<Ticket> tickets) {
        List<BigDecimal> sortedPrices = tickets.stream()
                .map(Ticket::price)
                .sorted()
                .toList();

        if (sortedPrices.size() % 2 != 0) {
            return sortedPrices.get(sortedPrices.size() / 2);
        } else {
            return sortedPrices.get(sortedPrices.size() / 2).add(sortedPrices.get(sortedPrices.size() / 2 - 1))
                    .divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN);
        }
    }
}
