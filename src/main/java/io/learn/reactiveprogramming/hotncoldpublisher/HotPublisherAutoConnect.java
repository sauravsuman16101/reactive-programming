package io.learn.reactiveprogramming.hotncoldpublisher;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotPublisherAutoConnect
{
    private static final Logger log = LoggerFactory.getLogger(HotPublisherAutoConnect.class);

    public static void main(String[] args)
    {
        playMovieOnTvChannel();
    }

    private static void playMovieWithAutoConnect()
    {
        var movieFlux = movieStream().publish().autoConnect(); // required minimum 1 subscriber by default similar to ref count 1 to start the movie

        Util.sleepSeconds(2);
        movieFlux
                .take(4) // didn't want to continue the movie
                .subscribe(Util.subscriber(Util.faker().name().firstName()));

        Util.sleepSeconds(3);
        movieFlux
                .take(3) // don't want to continue the movie
                .subscribe(Util.subscriber(Util.faker().name().firstName()));

        // Movie continues to play even when both the subscribers left

        Util.sleepSeconds(15);
    }

    private static void playMovieOnTvChannel()
    {
        var movieFlux = movieStream().publish().autoConnect(0); // Don't require any subscriber to start the movie just like TV Channels
        Util.sleepSeconds(2);
        movieFlux
                .take(4) // didn't want to continue the movie
                .subscribe(Util.subscriber(Util.faker().name().firstName()));

        Util.sleepSeconds(3);
        movieFlux
                .take(3) // don't want to continue the movie
                .subscribe(Util.subscriber(Util.faker().name().firstName()));

        // Movie continues to play even when both the subscribers left

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
