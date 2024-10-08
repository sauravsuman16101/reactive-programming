package io.learn.reactiveprogramming.helper;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class NameGenerator
{
    private static final Logger log = LoggerFactory.getLogger(NameGenerator.class);
    private static final List<String> redisCache = new ArrayList<>();
    /**
     * Generates a list of random full names.
     *
     * @param count the number of names to generate
     * @return a list containing the generated names
     */
    public static List<String> getNameList(int count)
    {
        System.out.println("Generating name..."); // Inform the user that name generation is starting
        return IntStream.rangeClosed(1, count) // Create a stream of integers from 1 to count
                .mapToObj(i -> generateName()) // Generate a name for each integer in the stream
                .toList(); // Collect the generated names into a list
    }

    /**
     * Generates a Flux (reactive stream) of random full names.
     *
     * @param count the number of names to generate
     * @return a Flux that emits the generated names
     */
    public static Flux<String> getNameFlux(int count)
    {
        return Flux.range(1, count) // Create a Flux that emits integers from 1 to count
                .map(i -> generateName()); // Generate a name for each integer emitted by the Flux
    }

    public static Flux<String> generateNames()
    {
        return Flux.generate(sink ->
        {
            log.info("generating name");
            Util.sleepSeconds(1);
            var name = Util.faker().name().fullName();
            redisCache.add(name);
            sink.next(name);
        })
                .startWith(redisCache)
                .cast(String.class);
    }

    /**
     * Generates a single random full name.
     *
     * @return a randomly generated full name
     */
    private static String generateName()
    {
        Util.sleepSeconds(1); // Simulate a blocking I/O operation by sleeping for 1 second
        return Util.faker().name().fullName(); // Generate a random full name using the Faker library
    }


}

