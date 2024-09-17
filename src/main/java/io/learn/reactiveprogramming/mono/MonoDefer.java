package io.learn.reactiveprogramming.mono;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * This class demonstrates the use of Mono.defer() in Project Reactor.
 * It showcases how defer can be used to delay the creation of a Mono
 * until subscription time, which is particularly useful for time-consuming operations.
 */
public class MonoDefer
{
    private static final Logger log = LoggerFactory.getLogger(MonoDefer.class);

    /**
     * The main method demonstrates the use of Mono.defer() to delay
     * the creation of a publisher until it's subscribed to.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Mono.defer():
         * 1. Lazy Creation: Defers creation of the Mono until subscription time.
         * 2. Fresh Instance: Creates a new Mono instance for each subscriber.
         * 3. Dynamic Behavior: Allows for dynamic decision-making at runtime.
         * 4. Resource Efficiency: Useful for expensive operations or those depending on external state.
         * 5. Consistency: Ensures each subscriber gets the most up-to-date data or state.
         */

        //To delay creating publisher if it is time-consuming
        Mono.defer(MonoDefer::cretePublisher);
        //.subscribe(Util.subscriber()); //Uncomment to print sum

        /**
         * Mono.defer() takes a Supplier that returns a Mono.
         * In this case, it defers the call to cretePublisher() method.
         * The actual creation of the Mono is delayed until subscription.
         *
         * If the line with .subscribe() is uncommented:
         * 1. The cretePublisher() method will be called.
         * 2. The resulting Mono will emit the sum of the list elements.
         * 3. The subscriber will receive and print this sum.
         *
         * Without subscription, no computation occurs, demonstrating the lazy nature of defer.
         */
    }

    /**
     * Creates a Mono that represents a time-consuming operation.
     * This method simulates a costly operation by introducing delays.
     *
     * @return A Mono<Integer> that will emit the sum of a list of integers
     */
    private static Mono<Integer> cretePublisher()
    {
        log.info("Creating publisher");
        var list = List.of(1,2,3);
        Util.sleepSeconds(1);
        return Mono.fromSupplier(() -> sum(list));
    }

    /**
     * Simulates a time-consuming business logic operation.
     * It calculates the sum of a list of integers with an artificial delay.
     *
     * @param list The list of integers to sum
     * @return The sum of the integers in the list
     */
    private static Integer sum(List<Integer> list)
    {
        log.info("Finding the sum of {}", list);
        Util.sleepSeconds(3);
        return list.stream().mapToInt(i -> i).sum();
    }
}
