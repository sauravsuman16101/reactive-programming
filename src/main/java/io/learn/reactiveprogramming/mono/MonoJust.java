package io.learn.reactiveprogramming.mono;

import io.learn.reactiveprogramming.subscriber.SubscriberImpl;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

/**
 * This class demonstrates the use of Mono.just() in Project Reactor.
 * It showcases how to create a Mono that emits a single, predetermined value.
 */
public class MonoJust
{
    /**
     * The main method demonstrates different ways of using Mono.just(),
     * including subscription, request management, and integration with other APIs.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Use case 1: Basic Mono.just() creation and subscription
         * Creates a Mono that emits a single String value "Hello"
         */
        var mono = Mono.just("Hello");

        /**
         * Use case 2: Subscribing to a Mono with a custom subscriber
         * Demonstrates how to use a custom subscriber implementation with a Mono
         */
        var subscriber = new SubscriberImpl();
        mono.subscribe(subscriber);

        /**
         * Use case 3: Request management in a Mono
         * Shows how to request items from a Mono (though Mono only emits one item at most)
         */
        subscriber.getSubscription().request(Long.MAX_VALUE);

        /**
         * Note: Additional requests have no effect on a Mono as it emits at most one item
         */
        subscriber.getSubscription().request(Long.MAX_VALUE);

        /**
         * Use case 4: Cancellation of a Mono subscription
         * Demonstrates how to cancel a subscription to a Mono
         */
        subscriber.getSubscription().cancel();

        /**
         * Use case 5: Integration with methods expecting a Publisher
         * Shows how Mono.just() can be used to quickly create a Publisher for API integration
         */
        save(Mono.just("World"));

        /**
         * Additional use cases for Mono.just():
         *
         * 1. Providing default values:
         *    Mono<String> defaultMono = Mono.just("Default Value");
         *
         * 2. Wrapping known, synchronously available values:
         *    Mono<Integer> countMono = Mono.just(calculateCount());
         *
         * 3. Testing reactive streams:
         *    Mono<String> testMono = Mono.just("Test Data");
         *
         * 4. Combining with other reactive operations:
         *    Mono.just("Start")
         *        .flatMap(s -> performOperation(s))
         *        .map(result -> processResult(result));
         *
         * 5. Creating constant Monos for reuse:
         *    private static final Mono<String> EMPTY_RESULT = Mono.just("No Result");
         *
         * Note: Mono.just() is not suitable for wrapping intensive computations or I/O operations.
         * For those cases, consider using Mono.fromCallable() or Mono.fromSupplier().
         */
    }

    /**
     * Simulates saving data from a Publisher.
     * This method demonstrates how Mono can be used in APIs expecting a Publisher.
     *
     * @param publisher A Publisher of String, typically a Mono in this context
     */
    private static void save(Publisher<String> publisher)
    {
        // Just for example, no actual implementation
    }
}
