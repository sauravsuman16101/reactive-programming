package io.learn.reactiveprogramming.repeatretry.client;

import io.learn.reactiveprogramming.common.AbstractHttpClient;
import io.learn.reactiveprogramming.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.util.retry.Retry;

import java.time.Duration;

public class ExternalServiceDemo
{
    private static final Logger logger = LoggerFactory.getLogger(ExternalServiceDemo.class.getName());
    public static void main(String[] args)
    {
        retry();

        Util.sleepSeconds(60);
    }

    private static void repeat()
    {
        var client = new ExternalServiceClient(AbstractHttpClient.createDefaultHttpClient());
        client.getCountry()
                .repeat()
                .takeUntil(c -> c.equalsIgnoreCase("India"))
                .subscribe(Util.subscriber());
    }

    private static void retry()
    {
        var client = new ExternalServiceClient(AbstractHttpClient.createDefaultHttpClient());
        client.getProductName(2) //1 for client error and 2 for random server error
                .retryWhen(retryOnServerError())
                .subscribe(Util.subscriber());
    }

    private static Retry retryOnServerError()
    {

        return Retry.fixedDelay(20, Duration.ofSeconds(1))
                .filter(e -> e instanceof ServerError)
                .doBeforeRetry(rs -> logger.info("retrying {}", rs.failure().getMessage()));
    }

}
