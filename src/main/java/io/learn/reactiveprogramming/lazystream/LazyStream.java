package io.learn.reactiveprogramming.lazystream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

/**
 * This class demonstrates the lazy evaluation feature of Java streams.
 * It shows how operations on a stream are not executed until a terminal
 * operation is applied.
 */
public class LazyStream
{
    private static final Logger log = LoggerFactory.getLogger(LazyStream.class);

    /**
     * The main method demonstrates the lazy nature of streams by creating a stream
     * with an intermediate operation that is not executed immediately.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Laziness in Java Streams:
         * 1. Deferred Execution: Operations are not performed until a terminal operation is called.
         * 2. Efficiency: Only the necessary elements are processed, potentially saving resources.
         * 3. Pipelining: Multiple operations can be chained without immediate execution.
         * 4. Short-circuiting: Some operations can terminate early if the result is determined.
         * 5. Infinite Streams: Allows working with conceptually infinite sequences.
         */

        // Create a stream with a single element and add a peek operation
        Stream.of(1)
                .peek(i -> log.info("Peeked: {}", i));

        /**
         * At this point, no output will be produced. The peek operation is an
         * intermediate operation, which is lazy. It won't be executed until
         * a terminal operation is applied to the stream.
         */

        //Uncomment below to see the laziness
        //.toList();

        /**
         * If the .toList() line is uncommented, it will serve as a terminal operation.
         * This will trigger the execution of the stream pipeline, including the peek operation.
         * Only then will you see the log output: "Peeked: 1"
         *
         * Key points about stream laziness:
         * 1. Intermediate operations (like peek) are lazy and don't execute immediately.
         * 2. Terminal operations (like toList) trigger the execution of the entire stream pipeline.
         * 3. Without a terminal operation, intermediate operations are essentially no-ops.
         * 4. This laziness allows for optimization in more complex stream pipelines.
         */
    }
}
