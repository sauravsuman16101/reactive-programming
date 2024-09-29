package io.learn.reactiveprogramming.threadsnschedulers;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class SubscribeOnPublishOn
{
    private static final Logger log = LoggerFactory.getLogger(SubscribeOnPublishOn.class);

    public static void main(String[] args)
    {
        var flux = Flux.create(fluxSink -> {
                    for (int i = 1; i < 5; i++)
                    {
                        log.info("generating: {}", i);
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                })
                .publishOn(Schedulers.parallel())
                .doOnNext(v -> log.info("value {}", v))
                .doFirst(() -> log.info("first1"))
                .publishOn(Schedulers.newParallel("bar"))
                .subscribeOn(Schedulers.boundedElastic())
                .doFirst(() -> log.info("first2"));
        Runnable runnable = () -> flux

                .subscribe(Util.subscriber("Sub1"));
        Thread.ofPlatform().start(runnable);
        Util.sleepSeconds(2);


    }
}
