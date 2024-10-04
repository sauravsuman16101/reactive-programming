package io.learn.reactiveprogramming.batching.windowassignment;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class FileWriterExecution
{
    public static void main(String[] args)
    {
         var counter = new AtomicInteger(0);
         var fileNameFormat = "src/main/resources/window/file-%d.txt";
        eventStream()
                .window(Duration.ofMillis(1800))
                .flatMap(flux ->
                        FileWriter
                                .create(flux,
                                        Path.of(
                                                fileNameFormat.formatted(counter.incrementAndGet())
                                        )
                                )
                )
                .subscribe();

        Util.sleepSeconds(60);
    }

    private static Flux<String> eventStream()
    {
        return Flux.interval(Duration.ofMillis(300))
                .map(i -> "event-" + i+1);
    }
}
