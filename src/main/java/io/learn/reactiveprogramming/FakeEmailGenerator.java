package io.learn.reactiveprogramming;

import io.learn.reactiveprogramming.publisher.PublisherImpl;
import io.learn.reactiveprogramming.subscriber.SubscriberImpl;

import java.time.Duration;

/**
 * FakeEmailGenerator demonstrates various scenarios in reactive programming
 * using a simulated email generation system. It showcases different aspects
 * of Publisher-Subscriber interactions and error handling.
 */
public class FakeEmailGenerator
{
    /**
     * The main method runs different scenarios to demonstrate various
     * aspects of reactive programming and Publisher-Subscriber interactions.
     *
     * @param args Command line arguments (not used in this example)
     * @throws InterruptedException if any of the sleep operations are interrupted
     */
    public static void main(String[] args) throws InterruptedException {
        noEmail();
        itemsExceedScenario();
        cancelSubscriptionScenario();
        throwErrorScenario();
    }

    /**
     * Demonstrates a scenario where no emails are generated.
     * This method shows the basic setup of a Publisher and Subscriber
     * without any data being emitted.
     *
     * Use case: Testing the initial connection between Publisher and Subscriber.
     */
    public static void noEmail()
    {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        // No request is made, so no emails will be generated
    }

    /**
     * Demonstrates a scenario where the number of requested items exceeds
     * the maximum limit set by the Publisher.
     *
     * This scenario shows:
     * 1. How multiple requests are handled
     * 2. The Publisher's behavior when reaching its maximum item limit
     * 3. The continuous nature of subscriptions until a limit is reached
     *
     * Use case: Testing the Publisher's capacity and its behavior at the limit.
     *
     * @throws InterruptedException if the sleep operation is interrupted
     */
    public static void itemsExceedScenario() throws InterruptedException
    {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        // The last request might exceed the Publisher's limit
    }

    /**
     * Demonstrates a scenario where the subscription is cancelled mid-stream.
     *
     * This scenario shows:
     * 1. Normal operation of requesting and receiving items
     * 2. The effect of cancelling a subscription
     * 3. The behavior when trying to request items after cancellation
     *
     * Use case: Testing the cancellation mechanism and its effects on the stream.
     *
     * @throws InterruptedException if any of the sleep operations are interrupted
     */
    public static void cancelSubscriptionScenario() throws InterruptedException
    {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        subscriber.getSubscription().cancel();
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        // This last request should have no effect due to cancellation
    }

    /**
     * Demonstrates a scenario where an error is thrown due to excessive requests.
     *
     * This scenario shows:
     * 1. Normal operation up to a certain point
     * 2. The Publisher's error handling when requests exceed a certain threshold
     * 3. The Subscriber's behavior after an error is received
     *
     * Use case: Testing error handling and the system's resilience to excessive demands.
     *
     * @throws InterruptedException if any of the sleep operations are interrupted
     */
    public static void throwErrorScenario() throws InterruptedException
    {
        var publisher = new PublisherImpl();
        var subscriber = new SubscriberImpl();
        publisher.subscribe(subscriber);
        subscriber.getSubscription().request(3);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(11);
        Thread.sleep(Duration.ofSeconds(2));
        subscriber.getSubscription().request(3);
        // The large request of 11 items might trigger an error
    }
}

/**
 * Additional notes on the scenarios demonstrated:
 *
 * 1. No Email Scenario (noEmail()):
 *    - Demonstrates the basic setup without any data flow.
 *    - Useful for testing the initial connection and subscription process.
 *    - In real-world applications, this could represent a system ready state.
 *
 * 2. Items Exceed Scenario (itemsExceedScenario()):
 *    - Shows how the system handles multiple requests over time.
 *    - Demonstrates the Publisher's behavior when approaching and reaching its limit.
 *    - Illustrates the concept of backpressure in action.
 *    - In practice, this could represent handling peak loads or stress testing.
 *
 * 3. Cancel Subscription Scenario (cancelSubscriptionScenario()):
 *    - Illustrates the proper way to terminate a subscription.
 *    - Shows that cancelled subscriptions do not process further requests.
 *    - Important for resource management and stopping unnecessary processing.
 *    - In real systems, this could be used for user-initiated stops or system cool-down.
 *
 * 4. Throw Error Scenario (throwErrorScenario()):
 *    - Demonstrates error handling capabilities of the reactive system.
 *    - Shows how the system responds to requests exceeding predefined limits.
 *    - Illustrates the importance of proper error propagation in asynchronous systems.
 *    - In production, this could help in identifying and handling system misuse or overload.
 *
 * These scenarios collectively demonstrate key aspects of reactive programming:
 * - Asynchronous data flow
 * - Backpressure handling
 * - Error propagation
 * - Resource management through cancellation
 * - System behavior under various conditions (normal, excessive load, cancellation, errors)
 *
 * They provide a comprehensive test suite for the basic functionalities expected
 * in a reactive system, serving as both a demonstration and a set of test cases
 * for the PublisherImpl and SubscriberImpl implementations.
 */
