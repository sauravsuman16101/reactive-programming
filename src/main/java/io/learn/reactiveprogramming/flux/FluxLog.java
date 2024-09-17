package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates the use of the log() operator in Project Reactor.
 * The log() operator is a powerful tool for debugging and understanding
 * the behavior of reactive streams.
 */
public class FluxLog
{
    /**
     * The main method that demonstrates the use of log() operator
     * at different points in a Flux pipeline.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Importance of log() operator:
         * 1. Debugging: Helps identify issues in the reactive pipeline.
         * 2. Visibility: Provides insight into the internal workings of the stream.
         * 3. Signal tracking: Shows all reactive signals (onNext, onComplete, onError).
         * 4. Performance analysis: Can help identify bottlenecks or unexpected behavior.
         * 5. Educational tool: Great for learning and understanding Reactor's behavior.
         */

        Flux.range(1, 5)
                .log("range-map") // Log before map operation
                .map(i -> Util.faker().name().firstName())
                .log("map-subscribe") // Log after map operation
                .subscribe(Util.subscriber());

        /**
         * The log() operator:
         * - Can be placed anywhere in the pipeline
         * - Logs all upstream and downstream signals
         * - Helps visualize the flow of data and signals through the stream
         * - Is customizable with a category name for easy filtering in logs
         *
         * In this example:
         * - First log() shows signals between range() and map()
         * - Second log() shows signals between map() and subscribe()
         *
         * This setup allows us to see:
         * 1. How many items are emitted by range()
         * 2. How the map() operation transforms these items
         * 3. The timing and order of signals through the pipeline
         */
    }
}
