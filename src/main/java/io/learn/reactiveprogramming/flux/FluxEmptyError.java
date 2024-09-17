package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates the use of Flux.empty() and Flux.error() in Project Reactor.
 * These methods are useful for creating special Flux instances that represent
 * the absence of data or error conditions in reactive streams.
 */
public class FluxEmptyError
{
    /**
     * The main method that demonstrates the creation and subscription of empty and error Fluxes.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        /**
         * Use cases for Flux.empty():
         * 1. Representing no data: When a source has no items to emit.
         * 2. Default values: As a default return value when no actual data is available.
         * 3. Testing: To test how components handle empty streams.
         * 4. Completion signals: To indicate the end of a stream without emitting items.
         * 5. Conditional flows: In switchIf or flatMap operations when no alternative is needed.
         */
        Flux.empty()
                .log()
                .subscribe(Util.subscriber());
        /*
         * This will emit an onComplete signal since the Flux is empty.
         * No onNext signals will be emitted.
         */

        /**
         * Use cases for Flux.error():
         * 1. Error propagation: To propagate exceptions in a reactive way.
         * 2. Fallback mechanisms: In switchIfEmpty or onErrorResume to provide error states.
         * 3. Testing error handling: To test how components handle error conditions.
         * 4. Representing failure states: When an operation cannot produce a valid result.
         * 5. Circuit breaking: In combination with retry operators for fault tolerance.
         */
        Flux.error(new RuntimeException("Something went wrong"))
                .log()
                .subscribe(Util.subscriber());
        /*
         * This will emit an onError signal with the specified exception.
         * No onNext or onComplete signals will be emitted.
         */
    }
}
