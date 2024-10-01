package io.learn.reactiveprogramming.common;

import com.github.javafaker.Faker;
import org.reactivestreams.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.UnaryOperator;

/**
 * Utility class providing helper methods for reactive programming examples.
 */
public class Util
{
    private static final Logger log = LoggerFactory.getLogger(Util.class);

    // Static instance of Faker for generating fake data
    private static Faker faker = Faker.instance();

    /**
     * Creates a new DefaultSubscriber with an empty name.
     *
     * @param <T> The type of elements the subscriber will receive
     * @return A new DefaultSubscriber instance
     */
    public static <T> Subscriber<T> subscriber()
    {
        return new DefaultSubscriber<>("");
    }

    /**
     * Creates a new DefaultSubscriber with the specified name.
     *
     * @param <T> The type of elements the subscriber will receive
     * @param name The name to be assigned to the subscriber
     * @return A new DefaultSubscriber instance with the given name
     */
    public static <T> Subscriber<T> subscriber(String name)
    {
        return new DefaultSubscriber<>(name);
    }

    /**
     * Main method demonstrating the use of subscribers with a Mono.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a Mono that emits a single integer value
        var mono = Mono.just(1);
        // Subscribe to the Mono with two different named subscribers
        mono.subscribe(subscriber("sub1"));
        mono.subscribe(subscriber("sub2"));
    }

    /**
     * Provides access to the Faker instance for generating fake data.
     *
     * @return The static Faker instance
     */
    public static Faker faker()
    {
        return faker;
    }

    /**
     * Utility method to pause execution for a specified number of seconds.
     *
     * @param seconds The number of seconds to sleep
     * @throws RuntimeException if the sleep is interrupted
     */
    public static void sleepSeconds(int seconds)
    {
        try
        {
            Thread.sleep(Duration.ofSeconds(seconds));
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void sleep(Duration duration)
    {
        try
        {
            Thread.sleep(duration);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static <T> UnaryOperator<Flux<T>> fluxLogger(String name)
    {
        return flux -> flux
                .doOnSubscribe(s -> log.info("{} subscribed", name))
                .doOnCancel(() -> log.info("{} cancelled", name))
                .doOnComplete(() -> log.info("{} completed", name));
    }

}
