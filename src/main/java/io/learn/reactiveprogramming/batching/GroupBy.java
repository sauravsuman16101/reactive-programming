package io.learn.reactiveprogramming.batching;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class GroupBy
{
    public static final Logger log = LoggerFactory.getLogger(GroupBy.class);

    public static void main(String[] args)
    {
        Flux.range(1, 30)
                .delayElements(Duration.ofSeconds(1))
                .map(i -> i * 2)
                .startWith(1)
                .groupBy(i -> i % 2) // groups the elements based on the key 0 and 1
                .flatMap(GroupBy::process)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);


    }

    private static Mono<Void> process(GroupedFlux<Integer, Integer> groupedFlux)
    {
        log.info("received flux for {}", groupedFlux.key());
        return groupedFlux
                .doOnNext(i -> log.info("Key: " + groupedFlux.key() + ", Element: " + i))
                .doOnComplete(() -> log.info("Completed for " + groupedFlux.key()))
                .then();
    }
}
