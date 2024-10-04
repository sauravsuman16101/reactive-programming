package io.learn.reactiveprogramming.repeatretry;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class RepeatExample
{
    public static void main(String[] args)
    {
        Flux.just(1, 2, 3)
                .repeat(3)
                .subscribe(Util.subscriber());
        //demo5();

        Util.sleepSeconds(60);
    }

    private static void demo1()
    {
        var mono = Mono.fromSupplier(() -> Util.faker().country().name()); // non-blocking I/O
        var subscriber = Util.subscriber();
        mono
                .repeat() //repeats indefinitely only after it receives complete signal
                .subscribe(subscriber);
    }

    private static void demo2()
    {
        var mono = Mono.fromSupplier(() -> Util.faker().country().name()); // non-blocking I/O
        var subscriber = Util.subscriber();
        mono
                .repeat(3) //repeats 3 times only after it receives complete signal
                .subscribe(subscriber);
    }

    private static void demo3()
    {
        var mono = Mono.fromSupplier(() -> Util.faker().country().name()); // non-blocking I/O
        var subscriber = Util.subscriber();
        mono
                .repeat() //repeats 3 times only after it receives complete signal
                .takeUntil(c -> c.equalsIgnoreCase("India"))
                .subscribe(subscriber);
    }

    private static void demo4()
    {
        var mono = Mono.fromSupplier(() -> Util.faker().country().name()); // non-blocking I/O
        var subscriber = Util.subscriber();
        var atomicInteger = new AtomicInteger(0);
        mono
                .repeat(() -> atomicInteger.incrementAndGet() < 3) //repeats 3 times only after it receives complete signal
                .subscribe(subscriber);
    }

    private static void demo5()
    {
        var mono = Mono.fromSupplier(() -> Util.faker().country().name()); // non-blocking I/O
        var subscriber = Util.subscriber();
        mono
                .repeatWhen(flux -> flux.delayElements(Duration.ofSeconds(1)).take(2))//repeats 3 times only after it receives complete signal
                .subscribe(subscriber);
    }
}
