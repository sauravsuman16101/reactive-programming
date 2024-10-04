package io.learn.reactiveprogramming.appservicesexample;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class OrderService
{
    private static final Map<Integer, List<Order>> orderDB = Map.of(
            1, List.of(
                    new Order(1, Util.faker().commerce().productName(), Util.faker().random().nextInt(10, 100)),
                    new Order(1, Util.faker().commerce().productName(), Util.faker().random().nextInt(10, 100))
            ),
            2, List.of(
                    new Order(2, Util.faker().commerce().productName(), Util.faker().random().nextInt(10, 100)),
                    new Order(2, Util.faker().commerce().productName(), Util.faker().random().nextInt(10, 100)),
                    new Order(2, Util.faker().commerce().productName(), Util.faker().random().nextInt(10, 100))
            ),
            3, List.of()
    );

    public static Flux<Order> getUserOrders(Integer userId)
    {
        return Flux.fromIterable(orderDB.getOrDefault(userId, List.of()))
                .delayElements(Duration.ofMillis(500))
                .transform(Util.fluxLogger("order-for-user" + userId));
    }

}
