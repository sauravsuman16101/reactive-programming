package io.learn.reactiveprogramming.appservicesexample;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Mono;

public class MonoFlatMap
{
    public static void main(String[] args)
    {
                UserService.getUserId("Jack")
                        .flatMap(PaymentService::getUserBalance)
                        .subscribe(Util.subscriber());


    }
}
