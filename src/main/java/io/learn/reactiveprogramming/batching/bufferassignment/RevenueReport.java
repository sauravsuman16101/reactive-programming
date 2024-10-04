package io.learn.reactiveprogramming.batching.bufferassignment;

import java.time.LocalTime;
import java.util.Map;

public record RevenueReport(LocalTime time, Map<String, Integer> revenue)
{
}
