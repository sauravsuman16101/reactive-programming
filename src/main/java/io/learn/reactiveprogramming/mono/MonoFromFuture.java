package io.learn.reactiveprogramming.mono;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * This class demonstrates the integration of CompletableFuture with Reactor's Mono using Mono.fromFuture().
 * CompletableFuture is a powerful tool for asynchronous programming in Java, offering features like:
 * - Chaining of asynchronous operations
 * - Combining multiple futures
 * - Exception handling
 * - Timeout management
 */
public class MonoFromFuture
{
    private static final Logger log = LoggerFactory.getLogger(MonoFromFuture.class);

    /**
     * The main method showcases the conversion of CompletableFuture to Mono,
     * highlighting the differences in behavior and execution models.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Use case 1: Basic usage of Mono.fromFuture()
         * Demonstrates creating a Mono from a CompletableFuture and subscribing to it.
         *
         * CompletableFuture characteristics:
         * - Eager execution: Starts computing as soon as it's created
         * - Non-blocking: Allows other operations to proceed while it computes
         * - Composable: Can be chained with other CompletableFutures
         */
        Mono.fromFuture(MonoFromFuture::getName)
                .subscribe(Util.subscriber());

        /**
         * Note on CompletableFuture vs Mono:
         * - CompletableFuture executes eagerly, while Mono is lazy by default
         * - CompletableFuture uses the common ForkJoinPool for async operations, while Mono can use custom schedulers
         * - To make this behave more like Mono, we could wrap it in a supplier:
         *   Mono.fromFuture(() -> getName())
         * This defers the creation and execution of the CompletableFuture until subscription
         */

        /**
         * Use case 2: Handling asynchronous nature of CompletableFuture
         * CompletableFuture runs asynchronously, potentially on a different thread.
         * This sleep ensures the main thread doesn't exit before the async operation completes.
         *
         * CompletableFuture thread management:
         * - By default, uses ForkJoinPool.commonPool()
         * - Can be customized with specific Executors for more control over thread allocation
         */
        Util.sleepSeconds(1);

        /**
         * Additional CompletableFuture features that can be leveraged with Mono.fromFuture():
         *
         * 1. Chaining operations:
         *    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello")
         *        .thenApplyAsync(s -> s + " World");
         *    Mono.fromFuture(future)
         *
         * 2. Combining multiple futures:
         *    CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello");
         *    CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "World");

         *    Mono.fromFuture(combined)
         *
         * 3. Exception handling in CompletableFuture:
         *    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> { throw new RuntimeException("Error"); })
         *        .exceptionally(ex -> "Fallback");
         *    Mono.fromFuture(future)
         *
         * 4. Timeout handling (CompletableFuture approach):
         *    CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
         *        Thread.sleep(2000);
         *        return "Result";
         *    }).orTimeout(1, TimeUnit.SECONDS);
         *    Mono.fromFuture(future)
         */
    }

    /**
     * Simulates an asynchronous operation that returns a CompletableFuture.
     * CompletableFuture.supplyAsync() is used to run the operation asynchronously.
     *
     * Key CompletableFuture methods:
     * - supplyAsync(): Runs a Supplier asynchronously
     * - thenApply(): Transforms the result once it's available
     * - thenCompose(): Chains another CompletableFuture
     * - whenComplete(): Performs an action when the future completes
     *
     * @return A CompletableFuture<String> representing an asynchronously generated name
     */
    private static CompletableFuture<String> getName()
    {
        return CompletableFuture.supplyAsync(() ->
        {
            log.info("Generating Name");
            return Util.faker().name().fullName();
        });
    }
}
