package io.learn.reactiveprogramming.repeatretry;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class RetryExample
{
    private static final Logger logger = LoggerFactory.getLogger(RetryExample.class.getName());

    public static void main(String[] args)
    {
        demo3();

        Util.sleepSeconds(60);
    }

    public static void demo1()
    {
        getCountryName()
                .retry(2)
                .subscribe(Util.subscriber());
    }

    public static void demo2()
    {
        getCountryName()
                .retryWhen(
                        Retry.fixedDelay(2, Duration.ofSeconds(1))
                        .doBeforeRetry(rs -> logger.info("retrying {}", rs)))
                .subscribe(Util.subscriber());
    }

    public static void demo3()
    {
        getCountryName()
                .retryWhen(
                        Retry.fixedDelay(2, Duration.ofSeconds(1))
                                //.filter(err -> err instanceof IllegalArgumentException) //wont retry
                                .filter(err -> err instanceof RuntimeException)
                                .doBeforeRetry(rs -> logger.info("retrying {}", rs)))
                .subscribe(Util.subscriber());
    }

    private static Mono<String> getCountryName()
    {
        var atomicInteger = new AtomicInteger(0);

        return Mono.fromSupplier(() ->
        {
            if (atomicInteger.incrementAndGet() < 3) // put 5 to see retries exhausted exception
            {
                throw new RuntimeException("oops");
            }

            logger.info("Generating country name");
            return Util.faker().country().name();
        })
                .doOnError(err -> logger.error(err.getMessage()))
                .doOnSubscribe(s -> logger.info("subscribing"));

    }
}
