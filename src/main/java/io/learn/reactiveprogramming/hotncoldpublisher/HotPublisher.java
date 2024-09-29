package io.learn.reactiveprogramming.hotncoldpublisher;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotPublisher
{
    private static final Logger log = LoggerFactory.getLogger(HotPublisher.class);

    public static void main(String[] args)
    {
        playMovieScenario5();

    }

    private static void playMovieScenario1()
    {
        var movieFlux = movieStream().share(); // replacement for publish().refCount(1)

        Util.sleepSeconds(2);
        movieFlux.subscribe(Util.subscriber(Util.faker().name().firstName()));

        Util.sleepSeconds(3);
        movieFlux.subscribe(Util.subscriber(Util.faker().name().firstName()));

        Util.sleepSeconds(15);
    }

    private static void playMovieScenario2()
    {
        var movieFlux = movieStream().share();

        Util.sleepSeconds(2);
        movieFlux
                .subscribe(Util.subscriber(Util.faker().name().firstName()));

        Util.sleepSeconds(3);
        movieFlux
                .take(3) // didn't want to continue the movie and left
                .subscribe(Util.subscriber(Util.faker().name().firstName()));

        Util.sleepSeconds(15);
    }

    private static void playMovieScenario3()
    {

        var movieFlux = movieStream().share();

        Util.sleepSeconds(2);
        movieFlux
                .take(4) // didn't want to continue the movie
                .subscribe(Util.subscriber(Util.faker().name().firstName()));

        Util.sleepSeconds(3);
        movieFlux
                .take(3) // don't want to continue the movie
                .subscribe(Util.subscriber(Util.faker().name().firstName()));

        // When both the subscribers left the movie stops automatically as it required at least one subscriber to continue/start.

        Util.sleepSeconds(15);
    }

    private static void playMovieScenario5()
    {
        var movieFlux = movieStream().share(); // required minimum 1 subscriber to start the movie

        Util.sleepSeconds(2);
        movieFlux
                .take(1) // didn't want to continue the movie
                .subscribe(Util.subscriber(Util.faker().name().firstName()));

        // movie stops for a while and restart from beginning as there were no subscribers in between

        Util.sleepSeconds(3);
        movieFlux
                .take(3) // don't want to continue the movie
                .subscribe(Util.subscriber(Util.faker().name().firstName()));

        // movie stops after no subscriber left to watch the movie

        Util.sleepSeconds(15);
    }

    private static Flux<String> movieStream()
    {
        return Flux.generate(
                        () -> {
                            log.info("received the request");
                            return 1;
                        },
                        (state, sink) -> {
                            var scene = STR."movie scene \{state}";
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
