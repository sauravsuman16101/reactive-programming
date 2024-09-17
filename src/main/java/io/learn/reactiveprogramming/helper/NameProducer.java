package io.learn.reactiveprogramming.helper;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

/**
 * A producer class that generates and emits names to a FluxSink.
 * This class implements Consumer<FluxSink<String>> to allow for custom emission logic.
 */
public class NameProducer implements Consumer<FluxSink<String>>
{
    // The FluxSink to which names will be emitted
    private FluxSink<String> stringFluxSink;

    /**
     * Accepts a FluxSink and stores it for later use.
     * This method is called when the Flux is created and subscribed to.
     *
     * @param stringFluxSink The FluxSink to which names will be emitted
     */
    @Override
    public void accept(FluxSink<String> stringFluxSink)
    {
        this.stringFluxSink = stringFluxSink;
    }

    /**
     * Generates a random full name and emits it to the FluxSink.
     * This method can be called multiple times to produce multiple names.
     */
    public void produce()
    {
        // Generate a random full name using the Faker utility
        String name = Util.faker().name().fullName();
        // Emit the generated name to the FluxSink
        stringFluxSink.next(name);
    }
}
