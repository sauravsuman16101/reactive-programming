package io.learn.reactiveprogramming.operators;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates the use of various "do" hooks in Project Reactor.
 * These hooks allow for side effects and logging at different stages of the Flux lifecycle.
 */
public class FluxDoHooks
{
    private static final Logger log = LoggerFactory.getLogger(FluxDoHooks.class);

    public static void main(String[] args)
    {
        Flux.<Integer>create(fluxSink -> {
                    log.info("producer begins");
                    for (int i = 0; i < 4; i++)
                    {
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                    // fluxSink.error(new RuntimeException("oops"));
                    log.info("producer ends");
                })
                .doOnComplete(() -> log.info("doOnComplete-1"))
                .doFirst(() -> log.info("doFirst-1"))
                .doOnNext(item -> log.info("doOnNext-1: {}", item))
                .doOnSubscribe(subscription -> log.info("doOnSubscribe-1: {}", subscription))
                .doOnRequest(request -> log.info("doOnRequest-1: {}", request))
                .doOnError(error -> log.info("doOnError-1: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("doOnTerminate-1"))
                .doOnCancel(() -> log.info("doOnCancel-1"))
                .doOnDiscard(Object.class, o -> log.info("doOnDiscard-1: {}", o))
                .doFinally(signal -> log.info("doFinally-1: {}", signal))
                // .take(2)
                .doOnComplete(() -> log.info("doOnComplete-2"))
                .doFirst(() -> log.info("doFirst-2"))
                .doOnNext(item -> log.info("doOnNext-2: {}", item))
                .doOnSubscribe(subscription -> log.info("doOnSubscribe-2: {}", subscription))
                .doOnRequest(request -> log.info("doOnRequest-2: {}", request))
                .doOnError(error -> log.info("doOnError-2: {}", error.getMessage()))
                .doOnTerminate(() -> log.info("doOnTerminate-2"))
                .doOnCancel(() -> log.info("doOnCancel-2"))
                .doOnDiscard(Object.class, o -> log.info("doOnDiscard-2: {}", o))
                .doFinally(signal -> log.info("doFinally-2: {}", signal))
                //.take(4)
                .subscribe(Util.subscriber("subscriber"));
    }
}

/**
 * Explanation of "do" hooks and the order of operations:
 *
 * 1. doFirst(): Executed when the Flux is subscribed to, before any other operation.
 *    It's executed in reverse order of declaration (doFirst-2 runs before doFirst-1).
 *
 * 2. doOnSubscribe(): Called when a Subscription is created. Executed in order of declaration.
 *
 * 3. doOnRequest(): Invoked when a request for data is made by the subscriber.
 *
 * 4. doOnNext(): Called for each item emitted by the Flux. Executed in order of declaration.
 *
 * 5. doOnComplete(): Invoked when the Flux completes normally. Executed in order of declaration.
 *
 * 6. doOnError(): Called if the Flux terminates with an error. Executed in order of declaration.
 *
 * 7. doOnTerminate(): Executed when the Flux terminates, either by completion or error.
 *
 * 8. doOnCancel(): Invoked if the subscription is cancelled.
 *
 * 9. doOnDiscard(): Called for any item that is discarded.
 *
 * 10. doFinally(): Always called when the Flux terminates for any reason (complete, error, or cancel).
 *     It's executed in reverse order of declaration.
 *
 * Order of subscription, emission, and reception:
 * 1. Subscription: When subscribe() is called, it triggers the subscription process.
 *    - doFirst hooks are executed (in reverse order)
 *    - doOnSubscribe hooks are executed
 *    - doOnRequest hooks are executed
 *
 * 2. Emission: The Flux starts producing items.
 *    - For each item:
 *      - doOnNext hooks are executed
 *      - The item is passed to the subscriber
 *
 * 3. Completion/Error:
 *    - If completed normally:
 *      - doOnComplete hooks are executed
 *    - If an error occurs:
 *      - doOnError hooks are executed
 *    - In either case:
 *      - doOnTerminate hooks are executed
 *      - doFinally hooks are executed (in reverse order)
 *
 * 4. Cancellation (if it occurs):
 *    - doOnCancel hooks are executed
 *    - doFinally hooks are executed (in reverse order)
 *
 * Note: The commented out .take(2) and .take(4) operators demonstrate how you can limit
 * the number of items emitted, which would trigger doOnComplete earlier in the sequence.
 */
