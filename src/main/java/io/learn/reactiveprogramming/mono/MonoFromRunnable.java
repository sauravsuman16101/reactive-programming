package io.learn.reactiveprogramming.mono;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * This class demonstrates the use of Mono.fromRunnable() in Project Reactor.
 * It showcases how to create a Mono from a Runnable task, which is useful for
 * operations that don't produce a result but need to be executed in a reactive context.
 */
public class MonoFromRunnable
{
    private static final Logger log = LoggerFactory.getLogger(MonoFromRunnable.class);

    /**
     * The main method demonstrates the creation and usage of Mono from a Runnable,
     * highlighting its behavior and use cases.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Use case: Creating a Mono from getProductName method
         * This demonstrates how Mono.fromRunnable() can be used in a practical scenario.
         */
        getProductName(2).subscribe(Util.subscriber());
        /**
         * Differences between Mono.fromRunnable() and Mono.fromCallable():
         *
         * 1. Return Value:
         *    - fromRunnable(): Always completes empty (void). Used for side effects.
         *    - fromCallable(): Can return a value. Used when you need to produce a result.
         *
         * 2. Exception Handling:
         *    - fromRunnable(): Exceptions are propagated to the subscriber's onError.
         *    - fromCallable(): Exceptions are caught and emitted through onError, allowing for more granular error handling.
         *
         * 3. Use Cases:
         *    - fromRunnable(): Ideal for void operations, cleanup tasks, or triggering external systems.
         *    - fromCallable(): Suitable for computations that produce a result or may throw checked exceptions.
         *
         * 4. Functional Interface:
         *    - fromRunnable(): Uses java.lang.Runnable (no return, no checked exceptions).
         *    - fromCallable(): Uses java.util.concurrent.Callable (returns a value, can throw checked exceptions).
         */

        /**
         * Additional use cases for Mono.fromRunnable():
         *
         * 1. Cleanup operations:
         *    Mono.fromRunnable(() -> cleanupResources())
         *        .then(Mono.just("Cleanup completed"))
         *
         * 2. Logging or metrics recording:
         *    Mono.fromRunnable(() -> logEvent("Operation started"))
         *        .then(actualOperation())
         *
         * 3. Triggering external systems:
         *    Mono.fromRunnable(() -> notificationService.sendAlert())
         *        .then(Mono.just("Alert sent"))
         *
         * 4. Initialization tasks:
         *    Mono.fromRunnable(() -> initializeSystem())
         *        .then(Mono.defer(() -> actualOperation()))
         */
    }

    /**
     * Simulates fetching a product name based on a product ID.
     * This method demonstrates the use of both Mono.fromSupplier() and Mono.fromRunnable().
     *
     * @param productId The ID of the product
     * @return A Mono<String> representing the product name or an empty Mono with a side effect
     */
    private static Mono<String> getProductName(int productId)
    {
        if(productId == 1)
        {
            // For product ID 1, we return a Mono that will emit a product name
            return Mono.fromSupplier(() -> Util.faker().commerce().productName());
        }
        // For other product IDs, we return an empty Mono but perform a side effect
        return Mono.fromRunnable(() -> notifyBusiness(productId));
    }

    /**
     * Simulates notifying the business about an unavailable product.
     * This method demonstrates a side effect that doesn't produce a value.
     *
     * @param productId The ID of the unavailable product
     */
    private static void notifyBusiness(int productId)
    {
        log.info("Notify business on unavailable product {}", productId);
    }
}
