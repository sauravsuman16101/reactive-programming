package io.learn.reactiveprogramming.orderservice;

import reactor.core.publisher.Flux;

public interface OrderProcessor
{
    void consume(Order order);

    Flux<String> stream();
}
