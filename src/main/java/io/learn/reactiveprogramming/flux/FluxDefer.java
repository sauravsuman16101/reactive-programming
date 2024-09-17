package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * This class demonstrates the use of Flux.defer() in Project Reactor.
 * Flux.defer() is used to create a new Flux instance for each subscriber,
 * allowing for lazy evaluation and potentially different data for each subscription.
 */
public class FluxDefer
{
    /**
     * The main method that demonstrates the use of Flux.defer().
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Use case of Flux.defer():
         * 1. Lazy evaluation: The Flux is created only when subscribed to, not when defined.
         * 2. Per-subscriber state: Each subscriber gets its own instance of the Flux.
         * 3. Dynamic source: The data source can be determined at subscription time.
         * 4. Avoiding shared state: Ensures that each subscriber starts with a fresh state.
         *
         * In this example, defer() creates a new Flux from a List for each subscriber.
         * This could be useful if the List content might change between subscriptions,
         * or if you want to ensure each subscriber starts with a fresh iteration of the List.
         */
        Flux<Integer> deferredFlux = Flux.defer(() -> Flux.fromIterable(List.of(1, 2, 3)));

        // Subscribe to the deferred Flux
        deferredFlux.subscribe(Util.subscriber());

        // If we subscribe again, a new Flux instance will be created
        // This could potentially have different data if the List.of() was replaced with a dynamic data source
        deferredFlux.subscribe(Util.subscriber());
    }
}
