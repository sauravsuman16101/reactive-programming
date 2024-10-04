package io.learn.reactiveprogramming.batching.bufferassignment;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BookRevenueGeneration
{
    public static void main(String[] args)
    {
         var allowedCategories = Set.of(
                 "Science fiction",
                 "Fantasy",
                 "Mystery"
         );

         bookOrderStream()
                 .filter(order -> allowedCategories.contains(order.genre()))
                 .buffer(Duration.ofSeconds(5))
                 .log()
                 .map(BookRevenueGeneration::generateReport)
                 .subscribe(Util.subscriber());

         Util.sleepSeconds(60);

    }

    private static Flux<BookOrder> bookOrderStream()
    {
        return Flux.interval(Duration.ofMillis(200))
                .map(l -> BookOrder.create());
    }

    private static RevenueReport generateReport(List<BookOrder> orders)
    {
        Map<String, Integer> revenueByGenre = orders
                .stream()
                .collect(Collectors.groupingBy(
                        BookOrder::genre
                        , Collectors.summingInt(BookOrder::price)
                ));

        return new RevenueReport(LocalTime.now(),revenueByGenre);
    }
}
