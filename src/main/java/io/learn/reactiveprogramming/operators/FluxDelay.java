package io.learn.reactiveprogramming.operators;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * This class demonstrates the use of the delayElements operator in Project Reactor.
 * It shows how to introduce a time delay between emissions in a Flux.
 */
public class FluxDelay
{
    public static void main(String[] args)
    {
        // Create a Flux that emits integers from 1 to 10
        Flux.range(1, 10)
                .log("delaying...") // Log all signals (onNext, onComplete, onError) for debugging
                .delayElements(Duration.ofSeconds(1)) // Introduce a 1-second delay between each emission
                .subscribe(Util.subscriber()); // Subscribe to the Flux with a custom subscriber

        // Sleep the main thread to allow asynchronous operations to complete
        Util.sleepSeconds(12);
    }
}

/**
 * Definition:
 * The delayElements operator in Project Reactor introduces a time delay between
 * the emissions of elements in a Flux. It schedules each element to be emitted
 * after a specified time interval has elapsed since the previous element was emitted.
 *
 * Use Case:
 * 1. Rate Limiting: To control the rate at which elements are emitted, preventing
 *    overwhelming downstream components.
 * 2. Simulating Time-based Events: Useful for testing or simulating scenarios where
 *    events occur at regular intervals.
 * 3. Backpressure Management: Helps in managing backpressure by spacing out emissions.
 * 4. API Rate Limiting: When interacting with APIs that have rate limits, delayElements
 *    can help ensure those limits are not exceeded.
 *
 * Importance:
 * 1. Performance Optimization: Helps in scenarios where processing or downstream
 *    systems need time between elements to avoid overload.
 * 2. Resource Management: Assists in managing resources by controlling the flow of data.
 * 3. Testing and Simulation: Valuable for creating realistic test scenarios that
 *    involve time-based events.
 * 4. Compliance: Helps in adhering to external system constraints or SLAs that
 *    require specific timing between operations.
 * 5. Improved User Experience: In UI applications, can be used to create smooth
 *    animations or gradual updates.
 *
 * Note: The use of Util.sleepSeconds(12) is necessary in this example because
 * delayElements operates asynchronously. Without this sleep, the main thread
 * would exit before all elements are emitted. In a real application, you would
 * typically use more robust synchronization mechanisms or let the application
 * run continuously.
 */
