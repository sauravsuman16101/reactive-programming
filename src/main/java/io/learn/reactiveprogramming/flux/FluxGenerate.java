package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class FluxGenerate
{
    private static final Logger log = LoggerFactory.getLogger(FluxGenerate.class);

    public static void main(String[] args)
    {
        // Flux.generate is used when you want to generate values on-demand, based on the state of the application or the downstream pipeline.
        // It's useful when it's too expensive to calculate elements that may not be used downstream, or when the events you emit are influenced by the state of the app or the pipeline.
        Flux.generate(synchronousSink -> {
                    // The SynchronousSink is a callback provided by Flux.generate that allows you to emit values, errors, or complete the stream.
                    // In each invocation of the callback, you can only emit one of the following: onSubscribe, onNext, onError, or onComplete.
                    // This means that Flux.generate will calculate and emit values only when the downstream requests them.
                    synchronousSink.next(1);

                    // Uncomment the following line to see an error, as you can only emit one value per invocation of the callback.
                    // synchronousSink.next(2); //throw error as it can emit max. one value.

                    // You can also complete the stream from within the callback.
                    synchronousSink.complete();
                })
                // The subscriber will receive the emitted values.
                .subscribe(Util.subscriber());

        Flux.generate(synchronousSink -> {
                    log.info("invoked"); // Log when the callback is invoked
                    synchronousSink.next(1); // Emit the value 1
                })
                .take(4) // Take only the first 4 emissions
                .subscribe(Util.subscriber()); // Subscribe to the Flux and handle the emissions

        /*
         * This code demonstrates a use case for Flux.generate where you want to emit a fixed value (in this case, 1) repeatedly,
         * but only for a limited number of times (4 times, as specified by the take(4) operator).
         *
         * Flux.generate is particularly useful in scenarios where you want to generate values on-demand, based on the state of the application
         * or the downstream pipeline. In this specific example, the value being emitted (1) is not influenced by any external state,
         * but the number of emissions is controlled by the take(4) operator.
         *
         * The log.info("invoked") statement is used to log when the callback provided to Flux.generate is invoked. This can be helpful
         * for debugging purposes or to understand the flow of execution.
         *
         * The synchronousSink.next(1) line emits the value 1 to the downstream pipeline. The SynchronousSink is a callback provided by
         * Flux.generate that allows you to emit values, errors, or complete the stream. In each invocation of the callback, you can only
         * emit one of the following: onSubscribe, onNext, onError, or onComplete.
         *
         * Finally, the subscribe(Util.subscriber()) line subscribes to the Flux and handles the emissions. The Util.subscriber() method
         * likely returns a Subscriber implementation that logs or processes the emitted values in some way.
         *
         * In summary, this code demonstrates a simple use case of Flux.generate where a fixed value is emitted a limited number of times.
         * It showcases how Flux.generate can be combined with other operators like take to control the number of emissions, and how logging
         * can be used to understand the flow of execution.
         */

        /*
         * The main difference between Flux.create and Flux.generate is that Flux.create doesn't react to changes in the state of the app or the downstream pipeline,
         * while Flux.generate does.
         *
         * Flux.create is used when you want to calculate multiple (0...infinity) values that are not influenced by the state of your app or the downstream pipeline.
         * The method provided to Flux.create keeps calculating elements (or none), and the downstream will determine how many elements it wants.
         * If the downstream can't keep up, the elements that are already emitted will be buffered or dropped, based on the backpressure strategy.
         *
         * On the other hand, Flux.generate is used when you want to generate values on-demand, based on the state of the application or the downstream pipeline.
         * It's useful when it's too expensive to calculate elements that may not be used downstream, or when the events you emit are influenced by the state of the app or the pipeline.
         */
    }
}
