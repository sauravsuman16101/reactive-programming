package io.learn.reactiveprogramming.combinepublishers;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CollectList
{
    public static void main(String[] args)
    {
        Flux.range(1, 10)
                //.concatWith(Mono.error(new RuntimeException("oops")))
                .collectList()
                .subscribe(Util.subscriber());

    }
}
