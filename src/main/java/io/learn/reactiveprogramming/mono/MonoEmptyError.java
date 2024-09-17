package io.learn.reactiveprogramming.mono;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Mono;

/**
 * This class demonstrates different behaviors of Mono in Project Reactor,
 * specifically focusing on emitting values, empty Monos, and error scenarios.
 */
public class MonoEmptyError
{
    /**
     * The main method demonstrates various use cases of Mono creation and subscription,
     * including normal emission, empty Mono, and error scenarios.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Use case 1: Normal value emission
         * Demonstrates a Mono emitting a single value successfully.
         */
        getUsername(1).subscribe(Util.subscriber());
        System.out.println("=====================================");

        /**
         * Use case 2: Error scenario
         * Demonstrates how a Mono handles and propagates errors.
         */
        getUsername(2).subscribe(Util.subscriber());
        System.out.println("=====================================");

        /**
         * Use case 3: Custom error handling
         * Shows how to provide custom error handling when subscribing to a Mono.
         */
        //With custom consumer and error
        getUsername(3)
                .subscribe(
                        System.out::println,
                        //for catering on error dropped problem
                        err -> {}
                );

        /**
         * Additional use cases not explicitly shown but supported by the getUsername method:
         *
         * 4. Empty Mono: getUsername(2) would return an empty Mono, useful for representing
         *    the absence of a value without causing an error.
         *
         * 5. Fallback values: Mono.onErrorReturn() could be used to provide a default value
         *    in case of errors.
         *
         * 6. Retry logic: Mono.retry() could be applied to attempt recovery from transient errors.
         */
    }

    /**
     * Simulates fetching a username based on a user ID.
     * This method demonstrates different Mono behaviors based on the input.
     *
     * @param userId The ID of the user
     * @return A Mono<String> representing the username, an empty Mono, or an error
     */
    private static Mono<String> getUsername(int userId)
    {
        return switch (userId)
        {
            case 1 -> Mono.just("Foo"); // Normal case: returns a Mono with a value
            case 2 -> Mono.empty(); // Empty case: returns an empty Mono
            default -> Mono.error(new IllegalArgumentException("Invalid user id: " + userId)); // Error case: returns a Mono with an error
        };
    }
}
