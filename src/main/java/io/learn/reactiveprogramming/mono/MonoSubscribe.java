package io.learn.reactiveprogramming.mono;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * This class demonstrates various subscription methods for Mono in Project Reactor.
 * It showcases different ways to handle emissions, errors, and completion signals.
 */
public class MonoSubscribe
{
    private static final Logger log = LoggerFactory.getLogger(MonoSubscribe.class);

    /**
     * The main method demonstrates different subscription patterns for Mono,
     * including error handling, completion signals, and manual request management.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Create a simple Mono that emits a single integer value.
         * This Mono will be used to demonstrate various subscription methods.
         */
        var mono = Mono.just(1);

        /**
         * Uncomment the following line to simulate an error scenario.
         * This would transform the Mono to emit an error instead of a value.
         */
        //.map(i -> i/0);

        /**
         * Use case: Full subscription with error handling and completion signal
         *
         * This subscription method demonstrates:
         * 1. Handling the emitted value
         * 2. Error handling
         * 3. Completion signal handling
         * 4. Manual request management
         *
         * Key points:
         * - The first lambda handles the emitted value
         * - The second lambda handles any errors
         * - The third lambda handles the completion signal
         * - The fourth lambda allows manual request management
         */
        mono.subscribe(
                i -> log.info("Subscribed: {}", i),
                err -> log.error("error:", err),
                () -> log.info("completed"),
                subscription -> subscription.request(1)// or .cancel() to cancel the request
        );

        /**
         * Additional use cases for Mono subscriptions:
         *
         * 1. Simple value consumption:
         *    mono.subscribe(value -> log.info("Received: {}", value));
         *
         * 2. Error-only handling:
         *    mono.subscribe(null, error -> log.error("Error occurred", error));
         *
         * 3. Completion-only handling:
         *    mono.subscribe(null, null, () -> log.info("Processing completed"));
         *
         * 4. Combining with other reactive operations:
         *    mono.flatMap(value -> performAsyncOperation(value))
         *        .subscribe(result -> log.info("Final result: {}", result));
         *
         * 5. Conditional subscription:
         *    mono.filter(value -> value > 0)
         *        .subscribe(value -> log.info("Positive value: {}", value));
         *
         * 6. Subscription with side effects:
         *    mono.doOnNext(value -> auditLog.record(value))
         *        .subscribe(value -> processValue(value));
         */
    }
}
