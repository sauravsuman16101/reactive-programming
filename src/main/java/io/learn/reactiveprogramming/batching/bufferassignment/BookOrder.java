package io.learn.reactiveprogramming.batching.bufferassignment;

import io.learn.reactiveprogramming.common.Util;

public record BookOrder(String genre, String title, Integer price)
{
    public static BookOrder create()
    {
        return new BookOrder(
                Util.faker().book().genre(),
                Util.faker().book().title(),
                Util.faker().random().nextInt(100, 1000)
        );
    }
}
