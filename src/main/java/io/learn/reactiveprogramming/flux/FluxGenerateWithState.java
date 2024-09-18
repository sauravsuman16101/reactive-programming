package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

/**
 * The FluxGenerateWithState class demonstrates the use of Flux.generate to generate values on-demand
 * while maintaining a state. This is particularly useful when the values being generated depend on
 * the current state of the application or the downstream pipeline.
 *
 * In this example, the class generates random country names using the Faker library and emits them
 * until a specific condition is met (either a maximum number of emissions or the country "India" is generated).
 * The state being maintained is a counter that keeps track of the number of emissions.
 *
 * This use case showcases the flexibility of Flux.generate in generating values based on application state
 * and the ability to control the termination of the Flux based on custom conditions.
 */
public class FluxGenerateWithState
{
    public static void main(String[] args)
    {
        generateCountriesWithState();
    }

    private static void generateCountriesWithState()
    {
        // Flux.generate is used to generate values on-demand while maintaining a state
        // The state in this case is a counter that keeps track of the number of emissions
        Flux.generate(
                        () -> 0, // Initialize the state (counter) to 0
                        (counter, sink) -> { // The callback function to generate values and update the state
                            var country = Util.faker().country().name(); // Generate a random country name
                            sink.next(country); // Emit the generated country name
                            counter++; // Increment the counter
                            // Check if the maximum number of emissions (10) is reached or if the country "India" is generated
                            if (counter == 10 || country.equalsIgnoreCase("India"))
                            {
                                sink.complete(); // If either condition is met, complete the Flux
                            }
                            return counter; // Return the updated counter as the new state
                        }
                )
                .subscribe(Util.subscriber()); // Subscribe to the Flux and handle the emissions
    }
}
