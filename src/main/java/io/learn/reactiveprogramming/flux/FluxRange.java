package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates the use of Flux.range() in Project Reactor.
 * It showcases different ways to create and manipulate sequences of integers
 * using the range operator.
 */
public class FluxRange
{
    /**
     * The main method demonstrates various use cases of Flux.range()
     * and how it can be combined with other operators.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Use case 1: Basic range generation
         * Demonstrates creating a simple sequence of integers.
         *
         * Flux.range(start, count) generates a sequence of 'count' integers
         * starting from 'start'.
         */
        Flux.range(5, 10)
                .subscribe(Util.subscriber()); // Count starts from 5 and goes to 14.
        /**
         * This will emit integers from 5 to 14 (10 numbers in total).
         * Useful for generating sequences of numbers, like IDs or indices.
         */

        /**
         * Use case 2: Empty range
         * Demonstrates the behavior when count is zero.
         */
        Flux.range(5, 0)
                .subscribe(Util.subscriber());
        /**
         * This will not emit any items and will complete immediately.
         * Useful for testing or representing empty sequences.
         */

        /**
         * Use case 3: Combining range with map
         * Demonstrates how range can be used as a counter for generating other types of data.
         */
        Flux.range(1, 10)
                .map(i -> Util.faker().name().fullName())
                .subscribe(Util.subscriber());

        /**
         * This generates 10 random full names.
         * The range is used as a counter, and each number is mapped to a random name.
         * Useful for creating test data or simulating a sequence of events.
         */

        /**
         * Additional use cases not shown in the code but possible with Flux.range():
         *
         * 1. Pagination: Use range to generate page numbers for data fetching.
         *    Example: Flux.range(1, totalPages).flatMap(this::fetchDataForPage)
         *
         * 2. Retry mechanism: Use range as a counter for retry attempts.
         *    Example: Flux.range(1, maxRetries).flatMap(i -> attemptOperation())
         *
         * 3. Batch processing: Use range to create batch numbers.
         *    Example: Flux.range(1, totalBatches).flatMap(this::processBatch)
         *
         * 4. Time-based operations: Combine with delayElements for timed emissions.
         *    Example: Flux.range(1, 60).delayElements(Duration.ofSeconds(1))
         *             // Emits a number every second for a minute
         */
    }
}


