package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates the behavior of a Flux with multiple subscribers
 * and how different operations affect the emission of items to these subscribers.
 */
public class FluxMultipleSubscribers
{
    /**
     * The main method demonstrates how a single Flux can be subscribed to by multiple
     * subscribers and how intermediate operations affect the emissions.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Use cases for multiple subscribers:
         * 1. Broadcasting: Sending the same data to multiple consumers.
         * 2. Parallel processing: Different subscribers process the same data differently.
         * 3. Monitoring: One subscriber processes data while another logs or monitors.
         * 4. Redundancy: Multiple subscribers for fault tolerance or load balancing.
         * 5. Testing: Verifying behavior with different subscriber configurations.
         */

        // Create a Flux that emits the integers 1 through 6
        Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4, 5, 6);

        // Subscribe two subscribers to the integerFlux
        integerFlux.subscribe(Util.subscriber("sub1"));
        integerFlux.subscribe(Util.subscriber("sub2"));

        /**
         * Both sub1 and sub2 will receive all elements (1 through 6) from integerFlux.
         * This demonstrates that a Flux can have multiple independent subscribers,
         * each receiving the full sequence of emissions.
         */

        // Create a new Flux by filtering the integerFlux to only even numbers
        // and mapping each even number to a string by appending "a"
        integerFlux
                .filter(i -> i % 2 == 0) // Keep only even numbers
                .map(i -> i + "a") // Append "a" to each even number
                .subscribe(Util.subscriber("sub3")); // Subscribe a third subscriber

        /**
         * sub3 will only receive the transformed even numbers: "2a", "4a", "6a"
         * This demonstrates how intermediate operations (filter and map) affect
         * the emissions received by a subscriber.
         *
         * Key points about multiple subscriptions:
         * 1. Each subscription to the original Flux (integerFlux) receives all elements.
         * 2. Subscriptions after transformations (like filter and map) receive only the transformed elements.
         * 3. Subscriptions are independent; operations on one don't affect others.
         * 4. The original Flux is not modified by subscriptions or transformations.
         */
    }
}
