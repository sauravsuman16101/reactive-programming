package io.learn.reactiveprogramming.operators;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates the use of the handle() operator in Project Reactor.
 * The handle() operator is a powerful and flexible operator that combines
 * the functionalities of map() and filter() operators.
 */
public class FluxHandle
{
    public static void main(String[] args)
    {
        // Create a Flux that emits integers from 1 to 10
        Flux.range(1,10)
                // Filter out the number 8 before applying handle()
                .filter(i -> i != 8)
                // Apply the handle() operator
                .handle((item, sink) -> {
                    // Use a switch statement to demonstrate different handling scenarios
                    switch (item)
                    {
                        // For item 1, emit the string "One"
                        case 1 -> sink.next("One");
                        // For item 4, do nothing (effectively filtering it out)
                        case 4 -> {}
                        // For item 8, emit an error (note: this won't be reached due to the earlier filter)
                        case 8 -> sink.error(new RuntimeException("oops"));
                        // For all other items, emit the item as is
                        default -> sink.next(item);
                    }
                })
                // Subscribe to the Flux and handle emissions using a custom subscriber
                .subscribe(Util.subscriber());
    }
}

/**
 * Flux.handle() Operator:
 *
 * The handle() operator is a versatile operator in Project Reactor that allows for
 * fine-grained control over the emission process. It combines the functionality of
 * both map() and filter() operators, providing the ability to transform, filter,
 * and even generate new elements in a single step.
 *
 * Key features of handle():
 * 1. It takes a BiConsumer<T, SynchronousSink<R>> as its argument.
 * 2. For each source element, the BiConsumer is called with the element and a SynchronousSink.
 * 3. The SynchronousSink allows for emitting zero, one, or multiple elements for each source element.
 * 4. It can be used to filter elements by simply not calling sink.next() for certain items.
 * 5. It can transform elements by calling sink.next() with a modified value.
 * 6. It can generate new elements or split one element into multiple by calling sink.next() multiple times.
 * 7. It can complete the sequence early or emit errors using sink.complete() or sink.error().
 *
 * Use cases for handle():
 * 1. Complex filtering and transformation: When you need to both filter and transform elements
 *    based on complex conditions that are difficult to express with separate filter() and map() calls.
 * 2. Dynamic element generation: When you need to dynamically decide whether to emit elements,
 *    how many to emit, or what to emit based on the input.
 * 3. Error handling: When you need to selectively handle errors or convert certain elements into errors.
 * 4. State-based processing: When you need to maintain state across multiple elements to make decisions
 *    about emission (though be cautious about thread-safety in such cases).
 * 5. Debugging: It can be useful for adding breakpoints or logging at a granular level in the stream.
 *
 * In this example, we demonstrate several of these use cases:
 * - Filtering (case 4)
 * - Transformation (case 1)
 * - Error generation (case 8, though not reached due to earlier filter)
 * - Conditional processing (the switch statement)
 *
 * Note: While handle() is powerful, it's often clearer to use more specific operators when possible.
 * Use handle() when you need its unique combination of capabilities or when it significantly simplifies your code.
 */
