package io.learn.reactiveprogramming.mono;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * This class demonstrates the use of Mono.fromSupplier() in Project Reactor.
 * It showcases how to create a Mono from a Supplier, which is useful for
 * lazy evaluation and deferring expensive computations until they're needed.
 */
public class MonoFromSupplier
{
    private static final Logger logger = LoggerFactory.getLogger(MonoFromSupplier.class.getName());

    /**
     * The main method demonstrates different ways of creating Monos,
     * highlighting the benefits of using Mono.fromSupplier().
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
         * Use case 3: Mono.fromSupplier() without subscription
         * This shows how fromSupplier() defers execution until subscription.
         */
        //To delay the compute intensive operation. It won't do or print anything
        Mono.fromSupplier(() -> sum(list));

        /**
         * Use case 4: Mono.fromSupplier() with subscription
         * This demonstrates the proper use of fromSupplier(), executing only when subscribed.
         */
        Mono.fromSupplier(() -> sum(list)).subscribe(Util.subscriber());

        /**
         * Use case 5: Multiple subscriptions to Mono.fromSupplier()
         * This shows that each subscription triggers a new execution of the supplier.
         */
        Mono.fromSupplier(() -> sum(list)).subscribe(Util.subscriber());

        /**
         * Additional use cases for Mono.fromSupplier():
         *
         * 1. Lazy loading of resources:
         *    Mono<Resource> resourceMono = Mono.fromSupplier(() -> loadExpensiveResource());
         *
         * 2. Conditional Mono creation:
         *    Mono<String> conditionalMono = Mono.fromSupplier(() ->
         *        condition ? "Value A" : "Value B");
         *
         * 3. Integration with legacy blocking code:
         *    Mono<Data> dataMono = Mono.fromSupplier(() -> legacyService.fetchDataBlocking());
         *
         * 4. Caching with lazy initialization:
         *    Mono<ExpensiveObject> cachedMono = Mono.fromSupplier(() ->
         *        cache.computeIfAbsent("key", k -> new ExpensiveObject())).cache();
         *
         * 5. Deferred error handling:
         *    Mono<Result> resultMono = Mono.fromSupplier(() -> {
         *        if (someCondition) throw new CustomException("Error");
         *        return computeResult();
         *    });
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
