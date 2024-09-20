package io.learn.reactiveprogramming.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates different ways of subscribing to a Flux in Project Reactor.
 * It showcases the use of lambda expressions for handling various events in the Flux lifecycle.
 */
public class FluxSubscribe
{
    private static final Logger log = LoggerFactory.getLogger(FluxSubscribe.class);

    public static void main(String[] args)
    {
        // Create a Flux that emits integers from 1 to 10
        Flux<Integer> flux = Flux.range(1, 10);

        // Method 1: Subscribe with no arguments
        flux.subscribe();

        // Method 2: Subscribe with a consumer for onNext events
        flux.subscribe(i -> log.info("Received : {}", i));

        // Method 3: Subscribe with consumers for onNext and onError events

        // Method 4: Subscribe with consumers for onNext, onError, and onComplete events

        // Method 5: Using doOn* operators for side effects
        Flux.range(1,10)
                .doOnNext(i -> log.info("received: {}", i))
                .doOnComplete(() -> log.info("completed"))
                .doOnError(err -> log.error("error", err))
                .subscribe();
    }
}

/**
 * This class demonstrates various ways to subscribe to a Flux using lambda expressions:
 *
 * 1. flux.subscribe():
 *    - Simplest form of subscription.
 *    - It triggers the Flux to start emitting items.
 *    - No handling of emitted items, errors, or completion signals.
 *
 * 2. flux.subscribe(Consumer<? super T> consumer):
 *    - Provides a way to handle each emitted item.
 *    - The consumer is called for each item emitted by the Flux.
 *
 * 3. flux.subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer):
 *    - Handles both emitted items and errors.
 *    - The first consumer is called for each emitted item.
 *    - The second consumer is called if an error occurs.
 *
 * 4. flux.subscribe(Consumer<? super T> consumer, Consumer<? super Throwable> errorConsumer, Runnable completeConsumer):
 *    - Handles emitted items, errors, and completion.
 *    - The first consumer is called for each emitted item.
 *    - The second consumer is called if an error occurs.
 *    - The runnable is executed when the Flux completes normally.
 *
 * 5. Using doOn* operators:
 *    - An alternative way to handle events in the Flux lifecycle.
 *    - doOnNext: Called for each emitted item.
 *    - doOnComplete: Called when the Flux completes normally.
 *    - doOnError: Called if an error occurs.
 *    - These operators allow for side effects without affecting the Flux's behavior.
 *
 * Advantages of using lambda expressions for subscription:
 * 1. Conciseness: Lambda expressions provide a more compact way to define event handlers.
 * 2. Readability: The intent of each handler is clear and localized to the subscription point.
 * 3. Flexibility: Easy to customize behavior for different subscriptions without creating multiple subscriber classes.
 * 4. Immediate context: Variables in the surrounding scope are easily accessible (though care should be taken with closures).
 *
 * Comparison with custom Subscriber:
 * - Lambda approach: More suitable for simple, localized handling of Flux events.
 * - Custom Subscriber: Better for complex logic, reusable subscription behavior, or when you need to implement the full Subscriber interface.
 *
 * The lambda approach shown here is particularly useful for:
 * - Quick debugging or logging
 * - Simple transformations or side effects
 * - One-off subscriptions with specific behavior
 *
 * However, for more complex scenarios or when you need fine-grained control over the subscription process
 * (e.g., implementing backpressure), creating a custom Subscriber might be more appropriate.
 */
