package io.learn.reactiveprogramming.flightinfoexample;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class Udaan
{
    public static Flux<Flight> getFlights()
    {
        return Flux.merge(
                        IndianAirline.getFlights(),
                        Emirates.getFlights(),
                        Qatar.getFlights()
                )
                .take(Duration.ofSeconds(2));
    }
}
