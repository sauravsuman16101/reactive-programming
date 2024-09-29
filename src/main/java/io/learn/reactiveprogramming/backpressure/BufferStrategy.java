package io.learn.reactiveprogramming.backpressure;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class BufferStrategy
{
    private static final Logger log = LoggerFactory.getLogger(BufferStrategy.class);

    public static void main(String[] args)
    {
        var producer = Flux.create(sink -> {
            for (int i = 1; i <= 500 && !sink.isCancelled(); i++)
            {
                log.info("emitting {}", i);
                sink.next(i);
                Util.sleep(Duration.ofMillis(50));
            }
            sink.complete();
        })
                .cast(Integer.class)
                .subscribeOn(Schedulers.parallel());

        producer
                .onBackpressureBuffer()
                .limitRate(1)//do not produce more than 5 items per set
                .publishOn(Schedulers.boundedElastic()) //stops at 256
                .map(BufferStrategy::timeConsumingOperation)
                .subscribe(Util.subscriber("subscriber1"));

        Util.sleepSeconds(60);

    }

    private static int timeConsumingOperation(int i)
    {
        Util.sleepSeconds(1);
        return i;
    }
}
