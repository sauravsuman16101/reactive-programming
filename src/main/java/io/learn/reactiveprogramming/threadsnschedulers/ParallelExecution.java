package io.learn.reactiveprogramming.threadsnschedulers;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class ParallelExecution
{
    private static final Logger log = LoggerFactory.getLogger(ParallelExecution.class);

    public static void main(String[] args)
    {
        Flux.range(1, 10)
                .parallel(5)
                .runOn(Schedulers.parallel())
                .map(ParallelExecution::process)
                .sequential()
                .map(i -> i + "a")
                .subscribe(Util.subscriber());

        Util.sleepSeconds(3);

    }

    private static int process(int i)
    {
        log.info("Processing {}", i);
        Util.sleepSeconds(1);
        return i * 2;
    }


}
