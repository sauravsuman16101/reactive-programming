package io.learn.reactiveprogramming.flightinfoexample;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class IndianAirline
{
    private static final String AIRLINE = "IndianAirline";

    public static Flux<Flight> getFlights()
    {
        return Flux.range(1, Util.faker().random().nextInt(2, 10))
                .delayElements(Duration.ofMillis(Util.faker().random().nextInt(100, 1000)))
                .map(i -> new Flight( AIRLINE, Util.faker().random().nextInt(5000, 100000)))
                .transform(Util.fluxLogger(AIRLINE));
    }
}
