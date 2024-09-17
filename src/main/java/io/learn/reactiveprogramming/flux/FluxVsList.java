package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import io.learn.reactiveprogramming.helper.NameGenerator;
import io.learn.reactiveprogramming.subscriber.SubscriberImpl;

public class FluxVsList
{
    public static void main(String[] args) {
        /*
         * Flux is a reactive stream, which means that it emits 0 or more elements asynchronously and can be consumed by multiple subscribers.
         * List is a collection of elements, which means that it stores all the elements in memory synchronously.
         *
         * Flux is lazy, which means that it only emits elements when subscribed.
         * List is eager, which means that it stores all the elements when created.
         *
         * Flux is asynchronous, which means that it can emit elements asynchronously.
         * List is synchronous, which means that it can only store elements synchronously.
         */

        // Create a list of 5 random names
        var list = NameGenerator.getNameList(5);
        System.out.println(list); // Will block until all names are generated and print the names

        // Create a Flux that emits 5 random names
        NameGenerator.getNameFlux(5)
                .subscribe(Util.subscriber()); // Will not block, the producer keeps on pushing data to the subscriber

        // Create a custom subscriber
        var subscription = new SubscriberImpl();
        // Subscribe the custom subscriber to a Flux that emits 5 random names
        // Request the first 3 names from the Flux
        subscription.getSubscription().request(3);
        // Cancel the subscription after requesting the first 3 names
        subscription.getSubscription().cancel();
    }

}
