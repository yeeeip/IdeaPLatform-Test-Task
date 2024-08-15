package com.nuzhd.service;

import com.nuzhd.model.Ticket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.*;

public class TicketService {

    private Set<String> getCarriers(Ticket[] tickets) {
        Set<String> carriers = new HashSet<>();
        for (Ticket t : tickets) {
            carriers.add(t.carrier());
        }

        return carriers;
    }

    public Map<String, Duration> getMinFlightTimes(Ticket[] tickets) {
        Set<String> carriers = getCarriers(tickets);

        Map<String, Duration> result = new HashMap<>();
        for (String carrier : carriers) {
            result.put(carrier,
                    Arrays.stream(tickets)
                            .filter(t -> t.carrier().equals(carrier))
                            .map(Ticket::getFlightDuration)
                            .min(Comparator.naturalOrder())
                            .get()
            );
        }
        return result;
    }

    public BigDecimal getAvgPrice(Ticket[] tickets) {
        BigDecimal result = BigDecimal.ZERO;

        for (Ticket t : tickets) {
            result = result.add(t.price());
        }

        return result.divide(BigDecimal.valueOf(tickets.length), RoundingMode.HALF_EVEN);
    }

    public BigDecimal getMedianPrice(Ticket[] tickets) {
        List<BigDecimal> sortedPrices = Arrays.stream(tickets)
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
