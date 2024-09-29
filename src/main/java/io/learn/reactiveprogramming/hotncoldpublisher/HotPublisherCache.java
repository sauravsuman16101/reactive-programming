package io.learn.reactiveprogramming.hotncoldpublisher;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotPublisherCache
{
    // cache = replay + buffer
    // replay: replay the past data
    // buffer: store the future data

    private static final Logger log = LoggerFactory.getLogger(HotPublisherCache.class);

    public static void main(String[] args)
    {
        var movieFlux = stockStream().replay(2).autoConnect(0); // Don't require any subscriber to start the movie just like TV Channels
        Util.sleepSeconds(6);

        var sub1 = Util.faker().name().firstName();
        log.info("{} joining", sub1);
        movieFlux
                .subscribe(Util.subscriber(sub1));


        var sub2 = Util.faker().name().firstName();
        Util.sleepSeconds(4);

        log.info("{} joining", sub2);
        movieFlux
                .subscribe(Util.subscriber(sub2));

        // In stock price or live matches, you want to see the past as well as the current price or score at the time of joining which publish
        // autoConnect doesn't serve the requirement

        Util.sleepSeconds(15);
    }

    private static Flux<Integer> stockStream()
    {
        return Flux.generate(synchronousSink -> synchronousSink.next(Util.faker().random().nextInt(10, 100))).delayElements(Duration.ofSeconds(1))
                .delayElements(Duration.ofSeconds(2))
                .doOnNext(price -> log.info("emitting price: {}", price))
                .cast(Integer.class);
    }
}
