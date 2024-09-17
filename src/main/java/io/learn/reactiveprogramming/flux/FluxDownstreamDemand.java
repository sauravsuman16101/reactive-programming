package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.DefaultSubscriber;
import io.learn.reactiveprogramming.common.Util;
import io.learn.reactiveprogramming.subscriber.SubscriberImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates different approaches to handling downstream demand in Flux creation.
 * It showcases the differences between producing items early (potentially before demand)
 * and producing items on-demand (responding to downstream requests).
 */
public class FluxDownstreamDemand {
    private static final Logger log = LoggerFactory.getLogger(FluxDownstreamDemand.class);

    public static void main(String[] args) {
        // Invoke the method to produce items early, potentially before demand
        produceEarly();

        // Invoke the method to produce items on-demand, responding to downstream requests
        produceOnDemand();
    }

    /**
     * Demonstrates how the FluxSink can lead to backpressure if the producer is too fast
     * and the consumer is too slow, even when using the Flux.create method.
     *
     * Key characteristics of early production:
     * 1. Items are produced regardless of downstream demand.
     * 2. Can lead to backpressure issues if production outpaces consumption.
     * 3. May use more memory as items are buffered until requested.
     * 4. Suitable when data is readily available and memory is not a concern.
     */
    public static void produceEarly() {
        var subscriber = new SubscriberImpl();
        Flux.<String>create(fluxSink -> {
                    for (int i = 0; i < 10; i++) {
                        var name = Util.faker().name().firstName();
                        log.info("Emitting {}", name);
                        fluxSink.next(name); // Emit the name to the FluxSink immediately
                    }
                    fluxSink.complete(); // Signal completion of the Flux
                })
                .subscribe(subscriber);

        // Simulate delayed consumption
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(3);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(3);
        Util.sleepSeconds(2);
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(3); // This request will be ignored due to cancellation
    }

    /**
     * Demonstrates how to produce values on demand using the FluxSink's onRequest callback.
     * This approach prevents backpressure by emitting values only when the consumer requests them.
     *
     * Key characteristics of on-demand production:
     * 1. Items are produced only in response to downstream demand.
     * 2. Prevents backpressure issues by matching production to consumption rate.
     * 3. More efficient memory usage as items are not buffered unnecessarily.
     * 4. Suitable for scenarios where data production is expensive or time-consuming.
     */
    public static void produceOnDemand() {
        var subscriber = new SubscriberImpl();
        Flux.<String>create(fluxSink -> {
                    fluxSink.onRequest(request -> {
                        for (int i = 0; i < request && !fluxSink.isCancelled(); i++) {
                            var name = Util.faker().name().firstName();
                            log.info("Emitting {}", name);
                            fluxSink.next(name); // Emit the name to the FluxSink only when requested
                        }
                    });
                })
                .subscribe(subscriber);

        // Simulate controlled consumption
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(3);
        Util.sleepSeconds(2);
        subscriber.getSubscription().request(3);
        Util.sleepSeconds(2);
        subscriber.getSubscription().cancel();
        subscriber.getSubscription().request(3); // This request will be ignored due to cancellation
    }
}
