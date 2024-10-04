package io.learn.reactiveprogramming.batching;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class Buffer
{
    public static void main(String[] args)
    {
        demo4();

        Util.sleepSeconds(60);
    }

    private static void demo1()
    {
        eventStream()
                .buffer()// int-max val or the source has to complete
                .subscribe(Util.subscriber());
    }

    private static void demo2()
    {
        eventStream()
                .buffer(3)// every  3 events //problem with this scenario is that until and unless it gets 11, 12th item it won't execute
                .subscribe(Util.subscriber());
    }

    private static void demo3()
    {
        eventStream()
                .buffer(Duration.ofMillis(500))// every  500 ms
                .subscribe(Util.subscriber());
    }

    private static void demo4()
    {
        eventStream()
                .bufferTimeout(3, Duration.ofMillis(1000))
                .subscribe(Util.subscriber());
    }

    private static Flux<String> eventStream()
    {
        return Flux.interval(Duration.ofMillis(300))
                .take(10)
                .concatWith(Flux.never())
                .map(i -> "event-" + i+1);
    }
}
