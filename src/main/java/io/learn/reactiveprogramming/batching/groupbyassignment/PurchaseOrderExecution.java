package io.learn.reactiveprogramming.batching.groupbyassignment;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class PurchaseOrderExecution
{
    public static void main(String[] args)
    {
        orderStream()
                .filter(OrderProcessingService.canProcess())
                .groupBy(PurchaseOrder::category)
                .flatMap(gf -> gf.transform(OrderProcessingService.getProcessor(gf.key())))
                .subscribe(Util.subscriber());

        Util.sleepSeconds(60);
    }

    private static Flux<PurchaseOrder> orderStream()
    {
        return Flux.interval(Duration.ofMillis(100))
                .map(l -> PurchaseOrder.create());
    }
}
