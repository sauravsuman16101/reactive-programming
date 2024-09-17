package io.learn.reactiveprogramming.mono;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * This class demonstrates the use of Mono.fromCallable() in Project Reactor.
 * It showcases how to create a Mono from a potentially blocking or long-running operation.
 */
public class MonoFromCallable
{
    private static final Logger logger = LoggerFactory.getLogger(MonoFromCallable.class.getName());

    /**
     * The main method demonstrates different ways of creating and using Monos,
     * highlighting the benefits of Mono.fromCallable().
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        var list = List.of(1,2,3);

        /**
         * Use case 1: Immediate execution with Mono.just()
         * This approach executes the operation immediately, regardless of subscription.
         */
        //Runs fine
        Mono.just(sum(list)).subscribe(Util.subscriber());
        System.out.println("==============");

        /**
         * Use case 2: Mono.just() without subscription
         * This demonstrates that Mono.just() executes eagerly, even without a subscriber.
         */
        //Won't execute and waste CPU cycle
        Mono.just(sum(list));
        System.out.println("==============");

        /**
         * Use case 3: Mono.fromCallable() without subscription
         * This shows how fromCallable() defers execution until subscription.
         */
        //To delay the compute intensive operation. It won't do or print anything
        Mono.fromCallable(() -> sum(list));
        System.out.println("==============");

        /**
         * Use case 4: Mono.fromCallable() with subscription
         * This demonstrates the proper use of fromCallable(), executing only when subscribed.
         */
        Mono.fromCallable(() -> sum(list)).subscribe(Util.subscriber());

        /**
         * Additional use cases for Mono.fromCallable():
         *
         * 1. I/O Operations: Wrapping file reads or network calls that might block.
         *    Example: Mono.fromCallable(() -> Files.readAllBytes(Paths.get("file.txt")))
         *
         * 2. Database Queries: Encapsulating potentially slow database operations.
         *    Example: Mono.fromCallable(() -> databaseService.fetchLargeDataset())
         *
         * 3. Expensive Computations: Deferring CPU-intensive calculations until needed.
         *    Example: Mono.fromCallable(() -> complexAlgorithm.process(bigData))
         *
         * 4. External Service Calls: Wrapping calls to external APIs or services.
         *    Example: Mono.fromCallable(() -> externalService.makeApiCall())
         *
         * 5. Lazy Initialization: Deferring the creation of resource-heavy objects.
         *    Example: Mono.fromCallable(() -> new LargeObjectInitializer().create())
         */
    }

    /**
     * Simulates a compute-intensive operation by summing a list of integers.
     * This method is used to demonstrate the difference between eager and lazy execution.
     *
     * @param numbers The list of integers to sum
     * @return The sum of all integers in the list
     */
    private static int sum(List<Integer> numbers)
    {
        logger.info("Computing sum of {}", numbers);
        return numbers.stream().mapToInt(n -> n).sum();
    }
}
