package io.learn.reactiveprogramming.operators;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates the use of the SwitchIfEmpty operator in Project Reactor.
 */
public class SwitchIfEmpty
{
    public static void main(String[] args)
    {
        // Example: Using switchIfEmpty with Flux
        Flux.range(1,5)
                .filter(i -> i > 10)  // This filter will result in an empty Flux
                .switchIfEmpty(fallback())  // Switch to a fallback Flux if empty
                .subscribe(Util.subscriber());
    }

    /**
     * Provides a fallback Flux when the original Flux is empty.
     * @return Flux<Integer> as a fallback
     */
    private static Flux<Integer> fallback()
    {
        return Flux.range(100, 5);
    }
}

/**
 * Use Cases and Importance of switchIfEmpty Operator:
 *
 * 1. Fallback Data Source:
 *    - Use Case: Switching to a backup data source when the primary source is empty.
 *    - Importance: Enhances system resilience by providing alternative data paths.
 *
 * 2. Caching Strategies:
 *    - Use Case: Fetching from a remote source if cache is empty.
 *    - Importance: Improves performance while ensuring data availability.
 *
 * 3. Hierarchical Data Retrieval:
 *    - Use Case: Cascading through multiple data sources until data is found.
 *    - Importance: Allows for complex data retrieval strategies in distributed systems.
 *
 * 4. Default Behavior Implementation:
 *    - Use Case: Providing a set of default items when no specific items are available.
 *    - Importance: Ensures consistent behavior and user experience in various scenarios.
 *
 * 5. Error Recovery:
 *    - Use Case: Switching to a safe default stream when an error occurs and is handled as empty.
 *    - Importance: Adds another layer of fault tolerance to reactive applications.
 *
 * 6. A/B Testing:
 *    - Use Case: Switching between different data streams for experimentation.
 *    - Importance: Facilitates easy implementation of A/B testing in reactive systems.
 *
 * 7. Conditional Processing:
 *    - Use Case: Applying different processing logic based on data availability.
 *    - Importance: Allows for more complex and flexible reactive workflows.
 *
 * The switchIfEmpty operator in this example:
 * - Switches to a fallback Flux that emits integers 100 to 104 if the original Flux becomes empty after filtering.
 *
 * This pattern is crucial for building resilient and flexible reactive applications
 * that can adapt to various data availability scenarios.
 *
 * Differences between switchIfEmpty and defaultIfEmpty:
 *
 * 1. Type of Fallback:
 *    - defaultIfEmpty: Provides a single default value.
 *    - switchIfEmpty: Switches to an entirely new Publisher (Flux or Mono).
 *
 * 2. Complexity of Fallback:
 *    - defaultIfEmpty: Suitable for simple, static fallback values.
 *    - switchIfEmpty: Allows for complex, dynamic fallback streams.
 *
 * 3. Laziness:
 *    - defaultIfEmpty: The fallback value is eagerly evaluated.
 *    - switchIfEmpty: The fallback Publisher is lazily subscribed to only if needed.
 *
 * 4. Potential Emissions:
 *    - defaultIfEmpty: Always emits exactly one item if the source is empty.
 *    - switchIfEmpty: Can emit zero, one, or many items depending on the fallback Publisher.
 *
 * 5. Use Case Complexity:
 *    - defaultIfEmpty: Best for simple scenarios where a single default value suffices.
 *    - switchIfEmpty: Ideal for complex scenarios requiring conditional logic or alternative data sources.
 *
 * Choose switchIfEmpty when you need more flexibility and complexity in your fallback strategy,
 * and defaultIfEmpty when a simple, single-value fallback is sufficient.
 */
