package io.learn.reactiveprogramming.flightinfoexample;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Qatar
{
    private static final String AIRLINE = "Qatar";

    public static Flux<Flight> getFlights()
    {
        return Flux.range(1, Util.faker().random().nextInt(2, 10))
                .delayElements(Duration.ofMillis(Util.faker().random().nextInt(100, 1000)))
                .map(i -> new Flight( AIRLINE, Util.faker().random().nextInt(1000, 50000)))
                .transform(Util.fluxLogger(AIRLINE));
    }
}
