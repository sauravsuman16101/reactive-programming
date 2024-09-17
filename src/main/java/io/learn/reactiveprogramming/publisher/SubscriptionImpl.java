package io.learn.reactiveprogramming.publisher;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.logging.Logger;

/**
 * This class implements the Subscription interface from the Reactive Streams specification.
 * It manages the flow of data between a Publisher and a Subscriber, handling requests and cancellation.
 *
 * The Subscription interface is crucial in reactive programming as it enables backpressure,
 * allowing Subscribers to control the rate at which they receive items from Publishers.
 *
 * Example Description:
 * This implementation simulates a stream of email addresses, demonstrating:
 * - Controlled emission of data based on Subscriber requests
 * - Handling of maximum emission limits
 * - Error handling for excessive requests
 * - Proper completion signaling
 * - Cancellation support
 */
public class SubscriptionImpl implements Subscription
{
    private static final Logger log = Logger.getLogger(SubscriptionImpl.class.getName());
    private static final int MAX_ITERATIONS = 10;
    private final Faker faker;
    private final Subscriber<? super String> subscriber;
    private boolean cancelled;
    private int count = 0;

    /**
     * Constructor for SubscriptionImpl.
     * Initializes the Subscription with a Subscriber and sets up necessary resources.
     *
     * @param subscriber The Subscriber that will receive items through this Subscription.
     *
     * In this example:
     * - We initialize a Faker instance to generate random email addresses.
     * - We store the Subscriber to send items and signals to it.
     */
    public SubscriptionImpl(Subscriber<? super String> subscriber)
    {
        this.subscriber = subscriber;
        this.faker = Faker.instance();
    }

    /**
     * Implements the request method as defined in the Subscription interface.
     * This method is called by the Subscriber to request a certain number of items.
     *
     * @param requested The number of items requested by the Subscriber.
     *
     * Key aspects of the request method in this example:
     * - It respects backpressure by only sending the requested number of items.
     * - It handles cancellation and error scenarios.
     * - It enforces an upper limit (MAX_ITERATIONS) on the number of items to prevent unbounded requests.
     * - It generates and emits fake email addresses as the stream items.
     * - It signals completion when the maximum number of items has been emitted.
     */
    @Override
    public void request(long requested)
    {
        if(cancelled)
        {
            return;
        }
        if (requested > MAX_ITERATIONS)
        {
            this.subscriber.onError(new RuntimeException("Maximum number of iterations exceeded"));
            this.cancelled = true;
        }
        log.info("Subscriber has requested " + requested + " items");
        for(int i = 0; i < requested && count < MAX_ITERATIONS; i++)
        {
            count++;
            this.subscriber.onNext(this.faker.internet().emailAddress());
            if (count == MAX_ITERATIONS)
            {
                log.info("Subscriber has reached the maximum number of iterations");
                this.subscriber.onComplete();
                this.cancelled = true;
            }
        }
    }

    /**
     * Implements the cancel method as defined in the Subscription interface.
     * This method is called by the Subscriber to cancel the Subscription.
     *
     * In this example:
     * - Cancellation is implemented by setting a flag.
     * - This flag is checked in the request method to prevent further emissions after cancellation.
     */
    @Override
    public void cancel()
    {
        log.info("Subscription cancelled");
        this.cancelled = true;
    }

    /**
     * Importance and Use Cases of Subscription:
     *
     * 1. Backpressure Management:
     *    - Allows Subscribers to control the rate of incoming data, preventing overwhelming.
     *    - Essential for systems with mismatched production and consumption rates.
     *    - In this example: The Subscriber can request any number of items, and the Subscription respects this request.
     *
     * 2. Resource Optimization:
     *    - Enables efficient use of system resources by producing only what's needed.
     *    - Prevents unnecessary work and memory allocation.
     *    - In this example: Email addresses are generated on-demand, not pre-computed.
     *
     * 3. Flow Control:
     *    - Provides a mechanism for pausing and resuming data flow.
     *    - Useful in scenarios where processing needs to be temporarily halted.
     *    - In this example: The Subscriber can control the flow by making multiple request calls.
     *
     * 4. Error Handling:
     *    - Allows for graceful error propagation and handling in the reactive stream.
     *    - Crucial for building resilient and fault-tolerant systems.
     *    - In this example: An error is signaled if the request exceeds the maximum allowed items.
     *
     * 5. Cancellation Support:
     *    - Enables Subscribers to stop receiving data at any point.
     *    - Important for cleaning up resources and stopping unnecessary processing.
     *    - In this example: The cancel method allows the Subscriber to stop the stream at any time.
     *
     * 6. Custom Rate Limiting:
     *    - Facilitates implementation of custom rate-limiting strategies.
     *    - Useful in API consumption scenarios or when dealing with rate-limited resources.
     *    - In this example: A maximum limit (MAX_ITERATIONS) is enforced on the total number of emitted items.
     *
     * 7. Adaptive Streaming:
     *    - Allows for dynamic adjustment of data flow based on system conditions.
     *    - Enables building responsive systems that can adapt to varying loads.
     *    - In this example: The Subscription can adapt to varying request sizes from the Subscriber.
     *
     * 8. Testing and Debugging:
     *    - Provides hooks for intercepting and examining the flow of data in reactive streams.
     *    - Valuable for unit testing and troubleshooting reactive applications.
     *    - In this example: Logging statements help in tracking the flow of requests and emissions.
     *
     * This example demonstrates a simple yet practical implementation of a Subscription,
     * showcasing how it manages the flow of data (email addresses in this case) between
     * a Publisher and a Subscriber. It illustrates key concepts like backpressure,
     * cancellation, and completion in a reactive stream.
     */
}
