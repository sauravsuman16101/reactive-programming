package io.learn.reactiveprogramming.appservicesexample;

import reactor.core.publisher.Mono;

import java.util.Map;

public class PaymentService
{
    private static final Map<Integer, Double> userBalanceDB = Map.of(
            1, 1000.0,
            2, 2000.0,
            3, 3000.0
    );

    public static Mono<Double> getUserBalance(Integer userId)
    {
        return Mono.fromSupplier(() -> userBalanceDB.getOrDefault(userId, 0.0));
    }
}
