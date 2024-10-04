package io.learn.reactiveprogramming.appservicesexample;

import io.learn.reactiveprogramming.common.Util;

public class FlatMapMany
{
    public static void main(String[] args)
    {
        UserService.getUserId("John")
                .flatMapMany(OrderService::getUserOrders)
                .subscribe(Util.subscriber());

        Util.sleepSeconds(3);
    }
}
