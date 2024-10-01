package io.learn.reactiveprogramming.combinepublishers;

import io.learn.reactiveprogramming.common.Util;
import io.learn.reactiveprogramming.helper.NameGenerator;

public class StartWithUseCase
{
    public static void main(String[] args)
    {
         NameGenerator.generateNames()
                .take(2)
                .subscribe(Util.subscriber("S1"));

        NameGenerator.generateNames()
                .take(2)
                .subscribe(Util.subscriber("S2"));

        NameGenerator.generateNames()
                .take(3)
                .subscribe(Util.subscriber("S3"));
    }
}
