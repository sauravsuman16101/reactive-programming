package io.learn.reactiveprogramming.operators;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This class demonstrates various error handling techniques in Project Reactor.
 */
public class FluxErrorHandling
{
    private static final Logger log = LoggerFactory.getLogger(FluxErrorHandling.class);

    public static void main(String[] args)
    {
        //onErrorReturn();
        //onErrorResume();
        onErrorContinue();
        //onErrorComplete();
    }

    /**
     * Demonstrates the use of onErrorReturn operator.
     * This operator provides a fallback value when an error occurs.
     */
    private static void onErrorReturn()
    {
        Flux.range(1,10)
                .map(i -> i == 5 ? 5/0 : i)
                //Fallbacks on error
                .onErrorReturn(IllegalArgumentException.class, -1)
                .onErrorReturn(ArithmeticException.class, -2)
                .onErrorReturn(-3)
                .subscribe(Util.subscriber());
    }

    /**
     * Demonstrates the use of onErrorResume operator.
     * This operator allows switching to an alternative Flux when an error occurs.
     */
    private static void onErrorResume()
    {
        Flux.error(new RuntimeException("oops"))
                .onErrorResume(ArithmeticException.class, ex -> fallback1())
                .onErrorResume(ex -> fallback2())
                .onErrorReturn(-5) // default error handler
                .subscribe(Util.subscriber());
    }

    /**
     * Demonstrates the use of onErrorContinue operator.
     * This operator allows skipping elements that cause an error and continuing with the sequence.
     */
    private static void onErrorContinue()
    {
        Flux.range(1, 10)
                .map(i -> i == 5 ? 5/0 : i)
                .onErrorContinue((ex, value) ->  log.error("===> {}", value, ex))
                .subscribe(Util.subscriber());
    }

    /**
     * Demonstrates the use of onErrorComplete operator.
     * This operator completes the sequence when an error occurs.
     */
    private static void onErrorComplete()
    {
        Mono.error(new RuntimeException("oops"))
                .onErrorComplete()
                .subscribe(Util.subscriber());
    }

    // Helper methods for fallback scenarios
    private static Flux<Integer> fallback1()
    {
        return Flux.just(Util.faker().random().nextInt(10, 100));
    }

    private static Flux<Integer> fallback2()
    {
        return Flux.error(new IllegalArgumentException());
    }
}

/**
 * This class demonstrates four error handling techniques in Project Reactor:
 *
 * 1. onErrorReturn:
 *    - Use case: Provide a default value when an error occurs.
 *    - Description: When an error occurs, it emits a specified fallback value and completes the sequence.
 *    - In this example, different fallback values are provided based on the type of exception.
 *    - Best for: Simple error recovery where a default value is acceptable.
 *
 * 2. onErrorResume:
 *    - Use case: Switch to an alternative Flux or Mono when an error occurs.
 *    - Description: When an error occurs, it switches to an alternative publisher.
 *    - In this example, different fallback Fluxes are used based on the exception type.
 *    - Best for: Complex error recovery scenarios where you need to provide alternative data or perform additional operations.
 *
 * 3. onErrorContinue:
 *    - Use case: Ignore errors for specific elements and continue processing.
 *    - Description: When an error occurs, it logs the error and the offending element, then continues with the next element.
 *    - In this example, it logs the error and continues processing the remaining elements.
 *    - Best for: Scenarios where you want to skip problematic elements without terminating the entire sequence.
 *
 * 4. onErrorComplete:
 *    - Use case: Complete the sequence when an error occurs instead of propagating the error.
 *    - Description: When an error occurs, it completes the sequence without emitting the error.
 *    - In this example, it completes the Mono when an error occurs.
 *    - Best for: Scenarios where you want to silently complete the sequence on error, without providing a fallback value.
 *
 * Each method serves a different purpose in error handling:
 * - onErrorReturn is for providing simple fallback values.
 * - onErrorResume is for complex fallback logic or alternative data sources.
 * - onErrorContinue is for skipping errors and continuing processing.
 * - onErrorComplete is for silently completing the sequence on error.
 *
 * The choice of error handling method depends on the specific requirements of your application:
 * - Use onErrorReturn for simple fallback values.
 * - Use onErrorResume for complex fallback logic or alternative data sources.
 * - Use onErrorContinue when you want to skip errors and continue processing.
 * - Use onErrorComplete when you want to silently complete the sequence on error without providing a fallback.
 *
 * Note: The error handling operators are applied in the order they appear in the chain.
 * Only the first matching error handler will be executed.
 */
