package io.learn.reactiveprogramming.publisher;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/**
 * This class implements the Publisher interface from the Reactive Streams specification.
 * It demonstrates a basic implementation of a Publisher that emits String values.
 *
 * The Publisher interface is a fundamental part of reactive programming, representing
 * a provider of a potentially unbounded number of sequenced elements, publishing them
 * according to the demand received from its Subscribers.
 */
public class PublisherImpl implements Publisher<String>
{
    /**
     * Implements the subscribe method as defined in the Publisher interface.
     * This method is called when a Subscriber wants to receive items from this Publisher.
     *
     * @param subscriber The Subscriber that wants to receive items from this Publisher.
     *                   It must not be null as per the Reactive Streams specification.
     *
     * Key points about Publishers:
     * - Publishers are lazy by default and only start producing items when subscribed to.
     * - They can have multiple subscribers, each potentially receiving the same sequence of items.
     * - Publishers respect backpressure, only producing items as fast as the slowest Subscriber can handle.
     */
    @Override
    public void subscribe(Subscriber<? super String> subscriber)
    {
        // Create a new Subscription for this Subscriber
        var subscription = new SubscriptionImpl(subscriber);
        // Notify the Subscriber about the new Subscription
        subscriber.onSubscribe(subscription);
    }

    /**
     * Use cases and importance of Publishers:
     *
     * 1. Asynchronous data streams: Publishers are ideal for representing asynchronous
     *    sequences of data, such as events from a user interface or network responses.
     *
     * 2. Backpressure handling: Publishers allow for elegant handling of backpressure,
     *    preventing fast producers from overwhelming slow consumers.
     *
     * 3. Composability: Publishers can be easily composed and transformed using
     *    operators, allowing for complex data processing pipelines.
     *
     * 4. Resource management: Publishers can manage resources efficiently, releasing
     *    them when all Subscribers have cancelled or completed.
     *
     * 5. Scalability: The Publisher-Subscriber model allows for building scalable
     *    systems that can handle varying loads efficiently.
     *
     * 6. Interoperability: As part of the Reactive Streams specification, Publishers
     *    provide a standard interface for interoperability between different reactive libraries.
     *
     * 7. Event-driven programming: Publishers are well-suited for event-driven
     *    architectures, allowing for responsive and resilient system design.
     *
     * 8. Lazy evaluation: Publishers support lazy evaluation, potentially reducing
     *    unnecessary computations and improving performance.
     *
     * Importance in modern applications:
     * - Publishers are crucial in building responsive, resilient, and scalable applications.
     * - They enable better resource utilization by allowing non-blocking operations.
     * - Publishers facilitate the development of data streaming applications and microservices.
     * - They provide a standardized way to handle asynchronous data flows, simplifying
     *   complex application architectures.
     */
}
