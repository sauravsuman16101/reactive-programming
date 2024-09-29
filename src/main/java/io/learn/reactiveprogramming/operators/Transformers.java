package io.learn.reactiveprogramming.operators;

import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Transformers
{
    private static final Logger log = LoggerFactory.getLogger(Transformers.class.getName());

    record Customer(String name, int id)
    {
    }

    record PurchaseOrder(String productName, int price, int quantity)
    {

    }

    public static void main(String[] args)
    {
        var iDebugEnabled = false;
        getCustomers()
                .transform(iDebugEnabled ? addDebugger() : Function.identity())
                .subscribe();

        getPurchaseOrders()
                .transform(addDebugger())
                .subscribe();

    }

    private static Flux<Customer> getCustomers()
    {
        return Flux.range(1,3)
                .map(i -> new Customer(Util.faker().name().fullName(),i));
    }

    private static Flux<PurchaseOrder> getPurchaseOrders()
    {
        return Flux.range(1, 5)
                .map(i -> new PurchaseOrder(Util.faker().commerce().productName(), i * 100, i));
    }

    private static <T> UnaryOperator<Flux<T>> addDebugger()
    {
        return flux -> flux
                .doOnNext(i -> log.info("received: {}", i))
                .doOnComplete(() -> log.info("completed"))
                .doOnError(err -> log.error("error", err));
    }

}
