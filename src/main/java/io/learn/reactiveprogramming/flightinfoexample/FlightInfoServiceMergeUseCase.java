package io.learn.reactiveprogramming.flightinfoexample;

import io.learn.reactiveprogramming.common.Util;

public class FlightInfoServiceMergeUseCase
{
    public static void main(String[] args)
    {
        Udaan.getFlights()
                .takeUntil(flight -> flight.price() < 20000)
                .subscribe(Util.subscriber());
        Util.sleepSeconds(10);
    }
}
