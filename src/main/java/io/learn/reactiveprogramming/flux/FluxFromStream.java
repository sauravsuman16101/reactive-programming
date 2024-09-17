package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

/**
 * This class demonstrates the creation of Flux instances from Java Streams
 * using the Flux.fromStream() method in Project Reactor.
 * It also highlights important considerations when working with Streams in a reactive context.
 */
public class FluxFromStream
{
    /**
     * The main method that demonstrates the creation and subscription of Fluxes
     * from Java Streams, including potential pitfalls and best practices.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Use cases for Flux.fromStream():
         * 1. Bridging imperative and reactive code: Convert existing Stream-based code to reactive.
         * 2. Lazy evaluation: Leverage Stream's lazy nature in a reactive context.
         * 3. Infinite streams: Create potentially infinite Fluxes from infinite Streams.
         * 4. Parallel processing: Utilize parallel Streams in a reactive pipeline.
         * 5. Functional operations: Apply Stream's functional operations before converting to Flux.
         */

        var list = List.of(1, 2, 3, 4, 5);
        var stream = list.stream();

        /**
         * IMPORTANT: This approach can lead to errors if the Flux is subscribed to multiple times.
         * Streams can only be consumed once, so subsequent subscriptions will fail.
         */
        var fluxError = Flux.fromStream(stream);

        /**
         * First subscription works fine
         */
        fluxError.subscribe(Util.subscriber("First"));

        /**
         * Second subscription will throw an IllegalStateException
         * because the stream has already been operated upon or closed
         */
        // Uncomment the next line to see the error
        // fluxError.subscribe(Util.subscriber("Second"));

        /**
         * Best practice: Use a Supplier to create a new Stream for each subscription
         * This ensures that each subscriber gets its own fresh Stream to consume
         */

        /**
         * Both subscriptions work correctly with this approach
         */
        var fluxWorking = Flux.fromStream(() -> list.stream());
        fluxWorking.subscribe(Util.subscriber("Working - First"));
        fluxWorking.subscribe(Util.subscriber("Working - Second"));

        /**
         * Additional use case: Creating a Flux from an infinite Stream
         * Note: Be cautious with infinite Streams as they can lead to out-of-memory errors
         * if not properly bounded or controlled
         */
        Flux<Integer> infiniteFlux = Flux.fromStream(Stream.iterate(0, i -> i + 1));
        infiniteFlux.take(5) // Limit to first 5 elements
                .subscribe(Util.subscriber("Infinite Stream"));

        /**
         * Key takeaways:
         * 1. Streams are single-use, so direct use with Flux.fromStream() can cause issues.
         * 2. Use a Supplier to create a new Stream for each subscription for safety.
         * 3. Flux.fromStream() allows integration of Stream-based logic into reactive workflows.
         * 4. Be cautious with infinite Streams and use operators like 'take' or 'limit' to bound them.
         * 5. Flux.fromStream() preserves the lazy evaluation nature of Streams in a reactive context.
         */
    }
}
