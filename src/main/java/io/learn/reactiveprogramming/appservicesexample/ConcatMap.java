package io.learn.reactiveprogramming.appservicesexample;

import io.learn.reactiveprogramming.common.Util;

public class ConcatMap
{
    public static void main(String[] args)
    {
        UserService.getAllUsers()
                .map(User::Id)
                .concatMap(OrderService::getUserOrders)
                .subscribe(Util.subscriber());

            Util.sleepSeconds(10);
    }
}
