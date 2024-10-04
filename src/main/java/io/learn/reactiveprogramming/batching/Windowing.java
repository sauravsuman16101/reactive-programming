package io.learn.reactiveprogramming.batching;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Windowing
{
    public static void main(String[] args)
    {
        eventStream()
                //.window(5)
                .window(Duration.ofMillis(1800))
                .flatMap(stringFlux -> process(stringFlux))
                .subscribe();

        Util.sleepSeconds(60);
    }

    private static Flux<String> eventStream()
    {
        return Flux.interval(Duration.ofMillis(300))
                .map(i -> "event-" + i+1);
    }

    private static Mono<Void> process(Flux<String> flux)
    {
        return flux.doOnNext(e -> System.out.print("*"))
                .doOnComplete(System.out::println)
                .then();

    }
}
