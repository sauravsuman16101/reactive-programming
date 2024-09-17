package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * This class demonstrates the use of Flux.interval() in Project Reactor.
 * Flux.interval() creates a Flux that emits Long values periodically,
 * starting from 0 and incrementing with each emission.
 */
public class FluxInterval {

    /**
     * The main method that demonstrates the creation and subscription of a Flux
     * using Flux.interval().
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Use cases for Flux.interval():
         * 1. Periodic tasks: Executing tasks at regular intervals.
         * 2. Simulating time-based events: Useful for testing time-dependent scenarios.
         * 3. Polling: Regularly checking for updates or changes.
         * 4. Rate limiting: Controlling the rate of emissions in a stream.
         * 5. Heartbeat signals: Implementing keep-alive mechanisms in distributed systems.
         */

        // Create a Flux that emits a long value every 500 milliseconds
        Flux.interval(Duration.ofMillis(500))
                // Map each emitted long value to a random full name
                .map(l -> Util.faker().name().fullName())
                // Subscribe a subscriber to the Flux
                .subscribe(Util.subscriber());

        /**
         * Important characteristics of Flux.interval():
         * 1. Non-blocking: It uses a separate thread to emit values.
         * 2. Infinite: It continues emitting values indefinitely unless limited.
         * 3. Cold publisher: Each subscriber gets its own sequence starting from 0.
         * 4. Time-driven: Emissions are based on time, not data availability.
         */

        // Sleep for 5 seconds to allow the Flux to emit some values
        Util.sleepSeconds(5);

        /**
         * Note: We need to sleep the main thread because Flux.interval() operates on a
         * separate thread. Without this sleep, the program would exit immediately
         * after subscription, before any values are emitted.
         *
         * In real applications, you typically don't need this sleep as the program
         * usually continues running for other reasons.
         */
    }
}
