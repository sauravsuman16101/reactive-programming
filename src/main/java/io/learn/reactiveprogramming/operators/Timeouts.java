package io.learn.reactiveprogramming.operators;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.logging.Logger;

public class Timeouts
{
    private static final Logger log = Logger.getLogger(Timeouts.class.getName());

    public static void main(String[] args)
    {
        // Create a Mono that may take longer than expected to complete
        var mono = getProductName()
                .timeout(Duration.ofSeconds(1), fallback());
        mono
                .timeout(Duration.ofMillis(200)) // This timeout is closer to the subscriber
                //.timeout(Duration.ofMillis(5000)) // This timeout would never be reached
                .subscribe(Util.subscriber());

        // Sleep to allow asynchronous operations to complete
        Util.sleepSeconds(10);
    }

    /**
     * Simulates fetching a product name with a delay.
     * @return Mono<String> representing the product name
     */
    private static Mono<String> getProductName()
    {
        return Mono.fromSupplier(() -> "service -> " + Util.faker().commerce().productName())
                .delayElement(Duration.ofMillis(1900));
    }

    /**
     * Provides a fallback Mono in case of timeout.
     * @return Mono<String> as a fallback
     */
    private static Mono<String> fallback()
    {
        return Mono.fromSupplier(() -> "fallback -> " + Util.faker().commerce().productName())
                .delayElement(Duration.ofMillis(300))
                .doFirst(() -> log.info("do first"));
    }
}

/**
 * Use Cases and Importance of Multiple Timeouts:
 *
 * 1. Layered Response Times:
 *    - Use Case: Implementing different response time guarantees for various system layers.
 *    - Importance: Allows for fine-grained control over performance at different levels of the application.
 *
 * 2. Graceful Degradation:
 *    - Use Case: Providing increasingly faster but potentially less optimal responses as time progresses.
 *    - Importance: Ensures some level of service even under severe delays.
 *
 * 3. Differentiated SLA Compliance:
 *    - Use Case: Meeting different SLA requirements for various components or customer tiers.
 *    - Importance: Helps in maintaining differentiated service quality promises.
 *
 * 4. Cascading Fallback Mechanisms:
 *    - Use Case: Trying progressively simpler or cached data sources as timeouts occur.
 *    - Importance: Balances between providing the best possible data and ensuring a timely response.
 *
 * 5. Performance Profiling:
 *    - Use Case: Identifying bottlenecks at different stages of a complex operation.
 *    - Importance: Aids in pinpointing exactly where delays occur in a system.
 *
 * 6. Adaptive Timeout Strategies:
 *    - Use Case: Adjusting timeouts based on system load or other dynamic factors.
 *    - Importance: Provides flexibility to adapt to changing conditions in real-time.
 *
 * 7. Testing System Resilience:
 *    - Use Case: Verifying system behavior under various timing constraints.
 *    - Importance: Ensures the system degrades gracefully under different timing scenarios.
 *
 * Order of Execution for Multiple Timeouts:
 *
 * In Reactor, the timeout operator closer to the subscriber is evaluated first. This is crucial to understand because:
 *
 * 1. Inner Timeouts:
 *    - Timeouts defined earlier in the chain (further from the subscriber) act on their specific section of the pipeline.
 *    - They can provide more granular control over specific operations.
 *
 * 2. Outer Timeouts:
 *    - Timeouts defined later in the chain (closer to the subscriber) encompass all previous operations.
 *    - They provide an overall time limit for the entire sequence of operations.
 *
 * In this example:
 * 1. The first timeout (1 second) with fallback:
 *    - Acts on the getProductName() operation.
 *    - If triggered, it switches to the fallback() Mono.
 *
 * 2. The second timeout (200 milliseconds):
 *    - Acts on the entire sequence, including potential fallback.
 *    - Being closer to the subscriber, it's evaluated first and takes precedence.
 *
 * This order is important because:
 * - It allows for more fine-grained control over individual operations (inner timeouts).
 * - It provides an overall safeguard against excessive delays (outer timeouts).
 * - It enables a layered approach to timeout handling, with different strategies at different levels.
 *
 * Note: The commented-out 5-second timeout would never be reached in this scenario,
 * as the 200ms timeout would always trigger first. This illustrates the importance
 * of carefully considering the order and duration of timeouts in a chain.
 *
 * Understanding this execution order is crucial for designing effective and efficient
 * timeout strategies in reactive applications, ensuring responsiveness while allowing
 * for nuanced handling of timing issues at different stages of the operation.
 */
