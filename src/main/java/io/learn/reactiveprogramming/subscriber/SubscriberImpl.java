package io.learn.reactiveprogramming.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SubscriberImpl is a concrete implementation of the Subscriber interface from Reactive Streams.
 * It demonstrates how to create a basic subscriber that can receive and process a stream of String items.
 *
 * The Subscriber interface is a fundamental part of reactive programming:
 * - It defines the component that receives and processes items emitted by a Publisher.
 * - It allows for backpressure by controlling the rate at which it receives items.
 * - It handles the lifecycle of a subscription, including setup, item processing, error handling, and completion.
 *
 * Role of Subscriber in Reactive Programming:
 * 1. Consumption: Subscribers consume data emitted by Publishers in an asynchronous, non-blocking manner.
 * 2. Backpressure: They can control the rate of data flow, preventing overwhelming of the consumer.
 * 3. Error Handling: Subscribers define how errors in the data stream should be handled.
 * 4. Completion Handling: They specify actions to be taken when the data stream is complete.
 * 5. Decoupling: Subscribers help in decoupling the production of data from its consumption.
 */
public class SubscriberImpl implements Subscriber<String>
{
    private static final Logger log = LoggerFactory.getLogger(SubscriberImpl.class.getName());
    private Subscription subscription;

    /**
     * Called when the Subscriber is subscribed to a Publisher.
     * This method is invoked before any other methods of the Subscriber.
     *
     * @param subscription The Subscription that allows requesting items from the Publisher.
     */
    @Override
    public void onSubscribe(Subscription subscription)
    {
        this.subscription = subscription;
        // Note: In a typical implementation, you might want to request items here.
        // For example: subscription.request(Long.MAX_VALUE);
    }

    /**
     * Called when the Publisher emits a new item.
     * This method defines how each item in the stream is processed.
     *
     * @param item The item emitted by the Publisher.
     */
    @Override
    public void onNext(String item)
    {
        log.info("received: {}", item);
        // In a more complex implementation, you might process the item here.
        // You can also control backpressure by requesting more items if needed.
        // For example: subscription.request(1);
    }

    /**
     * Called when an error occurs in the stream.
     * This method defines how errors are handled in the subscription.
     *
     * @param throwable The error that occurred.
     */
    @Override
    public void onError(Throwable throwable)
    {
        log.error("error", throwable);
        // In a real-world scenario, you might want to implement more sophisticated error handling here.
    }

    /**
     * Called when the Publisher has no more items to emit.
     * This method is invoked to signal the completion of the stream.
     */
    @Override
    public void onComplete()
    {
        log.info("completed");
        // You might perform cleanup operations or final processing here.
    }

    /**
     * Getter method for the Subscription object.
     * This allows external access to the subscription, which can be useful for testing or manual control.
     *
     * @return The Subscription object associated with this Subscriber.
     */
    public Subscription getSubscription()
    {
        return subscription;
    }
}

/**
 * Additional notes on Subscribers in Reactive Programming:
 *
 * 1. Lifecycle:
 *    - onSubscribe() is called first, providing the Subscription object.
 *    - onNext() is called for each item, potentially many times.
 *    - onError() or onComplete() is called last, and only one of them will be called.
 *
 * 2. Backpressure:
 *    - Subscribers can implement backpressure by controlling when and how many items they request.
 *    - This is typically done using the Subscription.request() method.
 *
 * 3. Thread Safety:
 *    - Implementations must be thread-safe, as calls can come from different threads.
 *
 * 4. Responsiveness:
 *    - Methods should return quickly to avoid blocking the Publisher.
 *    - Heavy processing should be offloaded to other threads.
 *
 * 5. Error Handling:
 *    - onError() is crucial for graceful error management in asynchronous streams.
 *
 * 6. Completion:
 *    - onComplete() allows for final actions or cleanup when the stream ends normally.
 *
 * 7. Customization:
 *    - Subscribers can be customized for different behaviors, like filtering, transforming, or aggregating data.
 *
 * This implementation provides a basic framework for a Subscriber,
 * demonstrating the essential methods required. In real-world applications,
 * these methods would be expanded to include more sophisticated processing,
 * error handling, and backpressure management tailored to specific use cases.
 */
