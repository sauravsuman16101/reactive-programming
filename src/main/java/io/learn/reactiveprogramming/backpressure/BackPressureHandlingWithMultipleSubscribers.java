package io.learn.reactiveprogramming.backpressure;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class BackPressureHandlingWithMultipleSubscribers
{
    private static final Logger log = LoggerFactory.getLogger(BackPressureHandlingWithMultipleSubscribers.class);

    public static void main(String[] args)
    {
        //Queues

        System.setProperty("reactor.bufferSize.small", "16");
        var producer = Flux.generate(
                        () -> 1,
                        (state, sink) -> {
                            log.info("generating: {}", state);
                            sink.next(state);
                            return ++state;

                        }

                )
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());
        producer
                .limitRate(5)//do not produce more than 5 items per set
                .publishOn(Schedulers.boundedElastic()) //stops at 256
                .map(BackPressureHandlingWithMultipleSubscribers::timeConsumingOperation)
                .subscribe(Util.subscriber());

        producer
                .take(100)
                .publishOn(Schedulers.boundedElastic())
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);

    }

    private static int timeConsumingOperation(int i)
    {
        Util.sleepSeconds(1);
        return i;
    }
}
