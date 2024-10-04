package io.learn.reactiveprogramming.batching.groupbyassignment;

import io.learn.reactiveprogramming.common.Util;

public record PurchaseOrder(String item, String category, Integer price )
{
    public static PurchaseOrder create()
    {
        var commerce = Util.faker().commerce();
        return new PurchaseOrder(
               commerce.productName(),
               commerce.department(),
               Util.faker().random().nextInt(100, 1000)
        );
    }
}
