package io.learn.reactiveprogramming.operators;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates the use of Flux.generate() and handle() operators
 * to create a stream of random country names and process them.
 */
public class FluxCountryGenerator
{
    public static void main(String[] args)
    {
        Flux.<String>generate(synchronousSink -> {
                    String country = Util.faker().country().name(); // Generate a random country name
                    synchronousSink.next(country); // Emit the generated country name
                })
                .handle((country, sink) -> {
                    sink.next(country); // Re-emit the country name
                    if(country.equalsIgnoreCase("India"))
                    {
                        sink.complete(); // Complete the Flux if the country is India
                    }
                })
                .subscribe(Util.subscriber());
    }
}

/**
 * This example demonstrates two important concepts in Project Reactor:
 * 1. Flux.generate(): Used to create a Flux that generates values programmatically.
 * 2. Flux.handle(): A versatile operator that combines mapping and filtering capabilities.
 *
 * Flux.generate():
 * - It's used here to create an infinite stream of random country names.
 * - The lambda passed to generate() is called for each request from subscribers.
 * - It uses Util.faker() to generate a random country name and emits it using synchronousSink.next().
 * - This approach allows for on-demand generation of values.
 *
 * Flux.handle():
 * - It's a powerful operator that provides fine-grained control over element emission.
 * - In this example, it's used to implement a termination condition for the Flux.
 * - For each country name, it re-emits the name and checks if it's "India".
 * - If the country is "India", it completes the Flux using sink.complete().
 *
 * Use cases for Flux.handle():
 * 1. Conditional filtering and transformation: It allows complex logic for deciding
 *    whether to emit, transform, or filter out elements.
 * 2. Dynamic completion: As shown here, it can be used to dynamically decide when to
 *    complete a Flux based on the emitted values.
 * 3. Error injection: It can be used to introduce errors into the Flux based on certain conditions.
 * 4. State-based processing: It can maintain state across emissions to make decisions
 *    (though care must be taken with thread-safety).
 * 5. Element splitting or generation: It can emit multiple elements for a single input,
 *    or generate new elements.
 *
 * In this specific example:
 * - handle() is used for conditional completion of the Flux.
 * - It demonstrates how handle() can be used to implement complex termination logic
 *   that depends on the values being emitted.
 * - This approach is particularly useful when you need to process an indefinite stream
 *   of data but want to stop based on a specific condition.
 *
 * Note: While handle() is powerful, it's often clearer to use more specific operators
 * when possible. Use handle() when you need its unique combination of capabilities or
 * when it significantly simplifies your code.
 */
