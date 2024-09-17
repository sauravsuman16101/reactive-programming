package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * This class demonstrates the creation of Flux instances from arrays and lists
 * using Flux.fromArray() and Flux.fromIterable() methods in Project Reactor.
 */
public class FluxFromArrayOrList
{
    /**
     * The main method that demonstrates the creation and subscription of Fluxes
     * from both an array and a list.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Creating a Flux from an array using Flux.fromArray()
         *
         * Characteristics of Flux.fromArray():
         * 1. Works with fixed-size arrays
         * 2. Emits all elements at once
         * 3. Suitable for small, in-memory data sets
         * 4. Cannot handle dynamic changes to the source data
         */
        Integer[] array = {1, 2, 3, 4, 5};
        Flux.fromArray(array)
                .subscribe(Util.subscriber());

        /**
         * Creating a Flux from a List using Flux.fromIterable()
         *
         * Characteristics of Flux.fromIterable():
         * 1. Works with any Iterable (List, Set, Queue, etc.)
         * 2. Can potentially handle large or dynamically changing collections
         * 3. Emits elements one by one, allowing for better backpressure handling
         * 4. More flexible as it can work with custom Iterable implementations
         */
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        Flux.fromIterable(list)
                .subscribe(Util.subscriber());

        /**
         * Key differences between Flux.fromArray() and Flux.fromIterable():
         *
         * 1. Input type:
         *    - fromArray() works with arrays
         *    - fromIterable() works with any Iterable
         *
         * 2. Flexibility:
         *    - fromArray() is less flexible, limited to fixed-size arrays
         *    - fromIterable() is more flexible, can work with various collection types
         *
         * 3. Memory usage:
         *    - fromArray() typically loads all elements into memory at once
         *    - fromIterable() can potentially work with lazy-loaded or infinite streams
         *
         * 4. Performance:
         *    - fromArray() might be slightly faster for small data sets
         *    - fromIterable() can be more efficient for large or dynamically changing data sets
         *
         * 5. Backpressure handling:
         *    - fromArray() emits all elements at once, which might overwhelm slow consumers
         *    - fromIterable() can emit elements one by one, allowing for better backpressure handling
         *
         * Choose the appropriate method based on your data source and performance requirements.
         */
    }
}
