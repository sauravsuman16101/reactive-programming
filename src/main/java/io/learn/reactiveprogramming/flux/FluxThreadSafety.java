package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import io.learn.reactiveprogramming.helper.NameProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

public class FluxThreadSafety
{
    private static final Logger log = LoggerFactory.getLogger(FluxThreadSafety.class);

    public static void main(String[] args)
    {
        //threadUnsafeExample();
        threadSafeExample();
    }


    /**
     * Demonstrates a thread-unsafe example where multiple threads modify a shared ArrayList concurrently,
     * leading to potential data inconsistencies and race conditions.
     */
    public static void threadUnsafeExample() {
        var list = new ArrayList<Integer>(); // Create a shared ArrayList

        // Define a Runnable that adds 1000 elements to the shared ArrayList
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                list.add(1); // Add 1 to the shared ArrayList
            }
        };

        /*
         * Start 10 threads, each executing the Runnable that modifies the shared ArrayList.
         * Since the ArrayList is not thread-safe, multiple threads can access and modify it concurrently,
         * leading to potential data inconsistencies and race conditions.
         */
        for (int i = 0; i < 10; i++)
        {
            Thread.ofPlatform().start(runnable);
        }

        Util.sleepSeconds(3); // Wait for 3 seconds to allow the threads to run

        // Print the size of the ArrayList
        log.info("List size: {}", list.size());
        /*
         * The printed size may not be 10000 (10 threads x 1000 elements each) due to the thread-unsafe nature
         * of the ArrayList. The actual size can vary between runs due to race conditions and data inconsistencies.
         */
    }

    /**
     * Demonstrates a thread-safe example using Flux and Reactive Streams.
     * Multiple threads produce names concurrently, and the names are safely added to a shared list.
     */
    public static void threadSafeExample()
    {
        var list = new ArrayList<String>(); // Create a shared ArrayList to store names

        var nameProducer = new NameProducer(); // Create a NameProducer instance

        /*
         * Create a Flux using the NameProducer as the source.
         * The Flux will emit names produced by the NameProducer.
         */
        var flux = Flux.create(nameProducer)
                .subscribe(list::add); // Subscribe to the Flux and add emitted names to the shared list

        /*
         * Define a Runnable that calls the produce method of the NameProducer 1000 times.
         * Each call to produce will generate a new name and emit it through the Flux.
         */
        Runnable runnable = () ->
        {
            for (int i = 0; i < 1000; i++)
            {
                nameProducer.produce();
            }
        };

        /*
         * Start 10 threads, each executing the Runnable that produces names.
         * The produced names will be safely added to the shared list through the Flux.
         */
        for (int i = 0; i < 10; i++)
        {
            Thread.ofPlatform().start(runnable);
        }

        Util.sleepSeconds(3); // Wait for 3 seconds to allow the threads to run

        // Print the size of the list, which should be 10000 (10 threads x 1000 names each)
        log.info("List size: {}", list.size());
    }

}
