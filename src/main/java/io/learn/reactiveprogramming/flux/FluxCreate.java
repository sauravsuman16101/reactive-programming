package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates the use of Flux.create() to generate a custom Flux.
 * Flux.create() is a powerful method for creating a Flux from a custom source,
 * allowing fine-grained control over emissions and backpressure.
 */
public class FluxCreate {

    /**
     * The main method that demonstrates the creation and subscription of a custom Flux.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args) {
        // Create a Flux using the Flux.create method
        Flux.create(fluxSink -> {
                    /**
                     * Use cases for Flux.create():
                     * 1. Bridging non-reactive APIs: Adapt callback-based or synchronous APIs to Reactive Streams.
                     * 2. Custom emission logic: Implement complex emission patterns or business logic.
                     * 3. Backpressure handling: Manage backpressure with onRequest and onCancel callbacks.
                     * 4. Multiple emissions: Emit multiple items in a single invocation.
                     * 5. Asynchronous processing: Handle asynchronous data sources or events.
                     *
                     * In this example, we're using Flux.create() to implement custom emission logic
                     * that generates random country names until a specific condition is met.
                     */
                    String country;
                    do {
                        // Generate a random country name using the Faker library
                        country = Util.faker().country().name();

                        // Check if the generated country is not "canada"
                        if (!country.equalsIgnoreCase("canada")) {
                            // Emit the generated country to the Flux
                            fluxSink.next(country);
                        }
                    } while (!country.equalsIgnoreCase("canada")); // Continue generating countries until "canada" is generated

                    // Signal completion of the Flux
                    fluxSink.complete();

                    /**
                     * Note: Flux.create() provides a FluxSink, which offers methods like:
                     * - next(): Emit the next item
                     * - error(): Signal an error
                     * - complete(): Signal the completion of the Flux
                     * - onRequest(): Handle backpressure by controlling emissions based on request
                     * - onCancel(): Perform cleanup when the subscription is cancelled
                     */
                })
                // Subscribe a subscriber to the Flux
                .subscribe(Util.subscriber());
    }
}
