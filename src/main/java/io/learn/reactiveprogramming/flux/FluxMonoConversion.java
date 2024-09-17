package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This class demonstrates the conversion between Flux and Mono in Project Reactor.
 * It showcases methods to convert a Flux to a Mono and vice versa, highlighting
 * the flexibility of working with these two types of Publishers.
 */
public class FluxMonoConversion {

    /**
     * The main method demonstrates different conversion techniques between Flux and Mono.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Use cases for Flux to Mono conversion:
         * 1. When you need to work with a single element from a stream.
         * 2. To aggregate multiple elements into a single result.
         * 3. When interfacing with APIs that expect a Mono.
         * 4. To simplify error handling for multi-element streams.
         */
        var flux = Flux.range(1, 10); // Create a Flux that emits integers from 1 to 10

        // Convert the Flux to a Mono by using Mono.from(Publisher)
        Mono.from(flux)
                .subscribe(Util.subscriber());

        /**
         * Mono.from(flux) takes the first element emitted by the Flux.
         * This is useful when you're only interested in the first element
         * or when you want to ensure you're working with at most one element.
         */

        // Uncomment the following line to run the monoToFlux example
        // monoToFlux();
    }

    /**
     * Demonstrates the conversion from Mono to Flux.
     *
     * Use cases for Mono to Flux conversion:
     * 1. When you need to apply Flux operators to a single-element stream.
     * 2. To combine a single element with other streams.
     * 3. When working with APIs that expect a Flux.
     * 4. To handle optional values in a stream-like manner.
     */
    public static void monoToFlux()
    {
        var mono = getUserName(3); // Get a Mono<String> representing a username

        // Convert the Mono to a Flux by using Flux.from(Mono)
        save(Flux.from(mono));

        /**
         * Flux.from(mono) creates a Flux that emits the single element from the Mono.
         * This allows you to use Flux operators on a single-element stream,
         * or to combine this element with other Flux streams.
         */
    }

    /**
     * Simulates fetching a username based on a user ID.
     *
     * @param userId The ID of the user
     * @return A Mono<String> representing the username
     */
    public static Mono<String> getUserName(int userId)
    {
        return switch (userId) {
            case 1 -> Mono.just("John"); // Return a Mono with the value "John"
            case 2 -> Mono.empty(); // Return an empty Mono
            default -> Mono.error(new RuntimeException("User not found")); // Return a Mono with an error
        };
    }

    /**
     * Simulates saving a Flux of strings (usernames).
     *
     * @param flux The Flux of strings to save
     */
    public static void save(Flux<String> flux)
    {
        flux.subscribe(Util.subscriber());
    }
}
