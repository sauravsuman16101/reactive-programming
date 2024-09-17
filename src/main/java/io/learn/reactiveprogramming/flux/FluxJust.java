package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates the use of Flux.just() method in Project Reactor.
 * Flux.just() creates a Flux that emits the provided elements and then completes.
 */
public class FluxJust
{
    private static final Logger log = LoggerFactory.getLogger(FluxJust.class);

    /**
     * The main method that demonstrates the creation and subscription of a Flux
     * using Flux.just().
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Use cases for Flux.just():
         * 1. Creating test data: Quickly create a Flux with known values for testing.
         * 2. Wrapping existing data: Convert a fixed set of items into a reactive stream.
         * 3. Prototyping: Rapidly create simple Flux instances during development.
         * 4. Combining with other operators: Use as a source for more complex Flux chains.
         * 5. Default or fallback values: Provide a set of default items in error scenarios.
         */

        // Create a Flux with mixed data types using Flux.just()
        Flux.just(1, "foo", "bar", 2)
                .subscribe(Util.subscriber());

        /**
         * Key characteristics of Flux.just():
         * 1. Eager evaluation: All items are immediately available when the Flux is created.
         * 2. Fixed size: The number of items is known and finite.
         * 3. Synchronous: Items are emitted synchronously when subscribed.
         * 4. Replayable: The same sequence is emitted to all subscribers.
         * 5. Supports multiple data types: Can mix different types in a single Flux.
         */

        // Example of using Flux.just() with logging
        Flux.just("Hello", "World")
                .doOnNext(s -> log.info("Emitting: {}", s))
                .subscribe(Util.subscriber());

        /**
         * Note: Flux.just() is best used for a small, fixed number of items.
         * For larger or dynamic sets of data, consider other Flux creation methods
         * like Flux.fromIterable() or Flux.create().
         */
    }
}
