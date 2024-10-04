package io.learn.reactiveprogramming.combinepublishers;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Zip
{
    private static final Logger log = LoggerFactory.getLogger(Zip.class);
    /*
     * Zip operator is used to combine the values emitted by multiple publishers
     * into a single composite value.
     * It waits for all the publishers to emit their values before emitting the composite value.
     * The composite value is an array of the values emitted by the publishers.
     */

    record Car(String body, String engine, String tires){}

    public static void main(String[] args)
    {
        Flux.zip(getBody(), getEngine(), getTires())
                .map(tuple -> new Car(tuple.getT1(), tuple.getT2(), tuple.getT3()))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(5);
    }

    private static Flux<String> getBody()
    {
        return Flux.range(1, 5)
                .map(i -> STR."body-\{i}")
                .delayElements(Duration.ofMillis(100));
    }

    private static Flux<String> getEngine()
    {
        return Flux.range(1, 3)
                .map(i -> STR."engine-\{i}")
                .delayElements(Duration.ofMillis(200));
    }

    private static Flux<String> getTires()
    {
        return Flux.range(1, 10)
                .map(i -> STR."tires-\{i}")
                .delayElements(Duration.ofMillis(75));
    }


}
