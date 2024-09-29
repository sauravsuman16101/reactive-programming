package io.learn.reactiveprogramming.hotncoldpublisher;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class ColdPublisher
{
    private static final Logger log = LoggerFactory.getLogger(ColdPublisher.class);

    public static void main(String[] args)
    {
        //coldPublisher();
        var movieFlux = ottStream();

       Util.sleepSeconds(2);
        movieFlux.subscribe(Util.subscriber("foo"));

       Util.sleepSeconds(3);
        movieFlux.subscribe(Util.subscriber("bar"));

        Util.sleepSeconds(15);

    }

    private static void coldPublisher()
    {
        AtomicInteger atomicInteger = new AtomicInteger(0); //for shared state
        var flux = Flux.create(fluxSink -> {
            log.info("invoked");
            for (int i = 0; i < 5; i++)
            {
                fluxSink.next(atomicInteger.incrementAndGet());
                //fluxSink.next(i);
            }
            fluxSink.complete();
        });

        flux.subscribe(Util.subscriber("Sub1"));
        flux.subscribe(Util.subscriber("Sub2"));
        // state is being managed outside flux create, things get weird as subscriber 2 receives items from 6,7...
        //Provides separate stream for each subscriber because of cold publisher by default.
    }

    private static Flux<String> ottStream()
    {
        return Flux.generate(
                () -> {
                    log.info("received the request");
                    return 1;
                },
                (state, sink) -> {
                    var scene = "movie scene " + state;
                    log.info("playing {}", scene);
                    sink.next(scene);
                    return ++state;
                }
        )
                .take(10)
                .delayElements(Duration.ofSeconds(1))
                .cast(String.class);
    }
}
