package io.learn.reactiveprogramming.orderservice;

import io.learn.reactiveprogramming.common.AbstractHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;

import java.util.Objects;

public class OrderService extends AbstractHttpClient
{
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private Flux<Order> orderFlux;

    /**
     * Constructs an instance of the AbstractHttpClient.
     *
     * @param httpClient
     *         The HttpClient instance to be used for making HTTP requests.
     */
    public OrderService(HttpClient httpClient)
    {
        super(httpClient);
    }

    public Flux<Order> orderStream()
    {
        if(Objects.isNull(orderFlux))
        {
            this.orderFlux = getOrderStream();
        }
        return this.orderFlux;
    }

    public Flux<Order> getOrderStream()
    {
        return this.httpClient.get()
                .uri("/demo04/orders/stream")
                .responseContent()
                .asString()
                .map(this::parseOrder)
                .doOnNext(o -> log.info("{}", o))
                .publish()
                .refCount(2);
    }

    private Order parseOrder(String message)
    {
        // Implementation to parse the order string and create an Order object
        var arr = message.split(":");
        return new Order(
                arr[1],
                Integer.parseInt(arr[2]),
                Integer.parseInt(arr[3])
        );
    }
}
