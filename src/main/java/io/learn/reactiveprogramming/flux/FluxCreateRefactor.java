package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import io.learn.reactiveprogramming.helper.NameProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

/**
 * This class demonstrates a refactored approach to creating a custom Flux using Flux.create().
 * It showcases a more modular and reusable design compared to the original FluxCreate class.
 */
public class FluxCreateRefactor
{
    private static Logger log = LoggerFactory.getLogger(FluxCreateRefactor.class);
    /**
     * The main method that demonstrates the creation and subscription of a custom Flux
     * using a separate NameProducer class.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        // Create an instance of NameProducer
        NameProducer nameProducer = new NameProducer();

        // Create a Flux using the NameProducer instance
        Flux.create(nameProducer)
                .subscribe(Util.subscriber());

        // Produce names by calling the produce() method multiple times
        for(int i = 0; i <= 10; i++)
        {
            nameProducer.produce();
        }
    }
}
