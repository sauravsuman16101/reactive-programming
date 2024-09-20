package io.learn.reactiveprogramming.operators;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This class demonstrates the use of the defaultIfEmpty operator in Project Reactor.
 */
public class DefaultIfEmpty
{
    public static void main(String[] args)
    {
        // Example 1: Using defaultIfEmpty with Mono
        Mono.empty()
                .defaultIfEmpty("fallback")  // Provide a default value if the Mono is empty
                .subscribe(Util.subscriber());

        // Example 2: Using defaultIfEmpty with Flux
        Flux.range(1,5)
                .filter(i -> i > 10)  // This filter will result in an empty Flux
                .defaultIfEmpty(99)   // Provide a default value if the Flux is empty
                .subscribe(Util.subscriber());
    }
}

/**
 * Use Cases and Importance of defaultIfEmpty Operator:
 *
 * 1. Handling Empty Results:
 *    - Use Case: Providing a default value when a database query returns no results.
 *    - Importance: Ensures that downstream operators always have a value to work with,
 *      preventing null pointer exceptions and simplifying error handling.
 *
 * 2. API Responses:
 *    - Use Case: Supplying a default response when an API call returns no data.
 *    - Importance: Maintains consistent data structure in responses, making it easier
 *      for clients to process the results.
 *
 * 3. User Interface Defaults:
 *    - Use Case: Showing default text or placeholder content when no data is available.
 *    - Importance: Improves user experience by always displaying something meaningful,
 *      rather than empty or null values.
 *
 * 4. Configuration Settings:
 *    - Use Case: Providing default configuration values when none are specified.
 *    - Importance: Ensures that applications have sensible defaults, reducing the need
 *      for extensive error checking and improving robustness.
 *
 * 5. Data Transformations:
 *    - Use Case: Ensuring a transformation always produces a result, even if the input is empty.
 *    - Importance: Simplifies data processing pipelines by guaranteeing non-empty outputs.
 *
 * 6. Testing and Debugging:
 *    - Use Case: Providing known default values in test scenarios.
 *    - Importance: Facilitates easier debugging and testing by ensuring predictable behavior
 *      even in edge cases.
 *
 * 7. Fallback Mechanisms:
 *    - Use Case: Implementing simple fallback logic in reactive streams.
 *    - Importance: Adds a layer of resilience to applications by always providing a value,
 *      even when the primary data source fails.
 *
 * The defaultIfEmpty operator in this example:
 * - For Mono: Provides a fallback string "fallback" if the Mono is empty.
 * - For Flux: Supplies the integer 99 if the Flux becomes empty after filtering.
 *
 * This pattern is crucial for building robust reactive applications that gracefully
 * handle empty or missing data scenarios. It helps in maintaining the flow of data
 * through the reactive pipeline, preventing unexpected terminations or null values.
 *
 * Note: While defaultIfEmpty is powerful, it should be used judiciously. In some cases,
 * it might be more appropriate to handle empty scenarios explicitly, especially if
 * different actions are required based on why the stream is empty.
 */
