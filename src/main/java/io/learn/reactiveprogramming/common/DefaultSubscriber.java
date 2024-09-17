package io.learn.reactiveprogramming.common;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A default implementation of the Subscriber interface for reactive streams.
 * This class provides basic logging for subscription events.
 *
 * @param <T> The type of elements handled by this subscriber
 */
public class DefaultSubscriber<T> implements Subscriber<T>
{
    // Logger for this class
    private static final Logger log = LoggerFactory.getLogger(DefaultSubscriber.class);

    // Name of this subscriber instance
    private final String name;

    /**
     * Constructs a new DefaultSubscriber with the given name.
     *
     * @param name The name to identify this subscriber in logs
     */
    public DefaultSubscriber(String name)
    {
        this.name = name;
    }

    /**
     * Called when the Subscriber is subscribed to a Publisher.
     * Requests the maximum possible number of items from the subscription.
     *
     * @param subscription The Subscription that allows requesting items
     */
    @Override
    public void onSubscribe(Subscription subscription)
    {
        subscription.request(Long.MAX_VALUE);
    }

    /**
     * Called when the Publisher emits an item.
     * Logs the received item.
     *
     * @param item The item emitted by the Publisher
     */
    @Override
    public void onNext(T item)
    {
        log.info(" {} received: {}", this.name, item);
    }

    /**
     * Called when the Publisher encounters an error.
     * Logs the error.
     *
     * @param throwable The error encountered by the Publisher
     */
    @Override
    public void onError(Throwable throwable)
    {
        log.error("{} error", this.name, throwable);
    }

    /**
     * Called when the Publisher completes its sequence.
     * Logs the completion event.
     */
    @Override
    public void onComplete()
    {
        log.info("{} completed!", this.name);
    }
}
