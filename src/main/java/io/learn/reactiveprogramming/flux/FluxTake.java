package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates various 'take' operators in Project Reactor's Flux.
 * It includes examples of take(), takeWhile(), and takeUntil() operators.
 */
public class FluxTake {

    /**
     * The main method that runs all the example methods to demonstrate different 'take' operators.
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args) {
        // Example demonstrating the use of take() operator
        takeExample();

        // Example showcasing the takeWhile() operator
        takeWhileExample();

        // Example illustrating the takeUntil() operator
        takeUntilExample();
    }

    /**
     * Demonstrates the use of the take() operator to limit the number of elements emitted by a Flux.
     * This method creates a Flux of 10 integers, but only takes the first 3 elements.
     */
    public static void takeExample() {
        Flux.range(1, 10)
                .log("take") // Log events with the category "take"
                .take(3) // Limit the number of elements emitted to 3
                .log("sub") // Log events after take() with the category "sub"
                .subscribe(Util.subscriber()); // Subscribe using a utility subscriber
    }

    /**
     * Demonstrates the use of the takeWhile() operator.
     * This method creates a Flux of 10 integers, but only takes elements while they are less than or equal to 3.
     */
    public static void takeWhileExample() {
        Flux.range(1, 10)
                .log("take") // Log events with the category "take"
                .takeWhile(i -> i <= 3) // Take elements while they are less than or equal to 3
                .log("sub") // Log events after takeWhile() with the category "sub"
                .subscribe(Util.subscriber()); // Subscribe using a utility subscriber
    }

    /**
     * Demonstrates the use of the takeUntil() operator.
     * This method creates a Flux of 10 integers, but takes elements until it encounters a value less than 3.
     */
    public static void takeUntilExample() {
        Flux.range(1, 10)
                .log("take") // Log events with the category "take"
                .takeUntil(i -> i < 3) // Take elements until encountering a value less than 3
                .log("sub") // Log events after takeUntil() with the category "sub"
                .subscribe(Util.subscriber()); // Subscribe using a utility subscriber
    }
}
