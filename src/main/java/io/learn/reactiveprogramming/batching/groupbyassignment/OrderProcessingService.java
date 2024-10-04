package io.learn.reactiveprogramming.batching.groupbyassignment;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class OrderProcessingService
{
    private static final Map<String, UnaryOperator<Flux<PurchaseOrder>>> PROCESSOR_MAP = Map.of(
            "Kids", kidsOrder(),
            "Automotive", automotiveOrder()
    );

    private static UnaryOperator<Flux<PurchaseOrder>> automotiveOrder()
    {
        return flux -> flux
                .map(po -> new PurchaseOrder(po.item(), po.category(), po.price() + 100));
    }

    private static UnaryOperator<Flux<PurchaseOrder>> kidsOrder()
    {
        return flux -> flux
                .flatMap(po -> getFreeKidsOrder(po).flux().startWith(po));
    }

    private static Mono<PurchaseOrder> getFreeKidsOrder(PurchaseOrder purchaseOrder)
    {
        return Mono.fromSupplier(() -> new PurchaseOrder(
                purchaseOrder.item() + "-FREE",
                purchaseOrder.category(),
                0
                ));
    }

    public static Predicate<PurchaseOrder> canProcess()
    {
        return po -> PROCESSOR_MAP.containsKey(po.category());
    }

    public static UnaryOperator<Flux<PurchaseOrder>> getProcessor(String category)
    {
        return PROCESSOR_MAP.get(category);
    }
}
