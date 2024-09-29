package io.learn.reactiveprogramming.orderservice;

import io.learn.reactiveprogramming.common.AbstractHttpClient;
import io.learn.reactiveprogramming.common.Util;

public class OrderServiceExecutor
{
    public static void main(String[] args)
    {
        var orderService = new OrderService(AbstractHttpClient.createDefaultHttpClient());
        var revenueService = new RevenueService();
        var inventoryService = new InventoryService();


        orderService.orderStream()
                .subscribe(inventoryService::consume);
        orderService.orderStream()
                .subscribe(revenueService::consume);

        inventoryService.stream().subscribe(Util.subscriber("inventory"));
        revenueService.stream().subscribe(Util.subscriber("revenue"));

        Util.sleepSeconds(30);
    }
}
