package io.learn.reactiveprogramming.appservicesexample;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

public class FluxFlatMap
{
    public static void main(String[] args)
    {
        UserService.getAllUsers()
                .map(User::Id)
                .flatMap(OrderService::getUserOrders, 1)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(3);

    }
}
