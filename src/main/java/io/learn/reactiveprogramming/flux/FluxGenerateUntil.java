package io.learn.reactiveprogramming.flux;

import io.learn.reactiveprogramming.common.Util;
import reactor.core.publisher.Flux;

/**
 * The FluxGenerateUntil class demonstrates two use cases of Flux.generate:
 *
 * 1. generateCountrySimple():
 *    This method showcases a simple use case of Flux.generate where it generates random country names
 *    using the Faker library and emits them until the country "India" is generated. When "India" is generated,
 *    the Flux is completed from within the callback provided to Flux.generate.
 *
 * 2. generateCountryUntil():
 *    This method demonstrates another use case of Flux.generate where it generates random country names
 *    using the Faker library, but instead of completing the Flux from within the callback, it uses the
 *    takeUntil operator to stop emitting values once the country "India" is generated.
 *
 * Both methods use Flux.generate to generate values on-demand, based on the state of the application
 * (in this case, the randomly generated country name). The SynchronousSink callback provided to Flux.generate
 * allows emitting the generated country name and completing the Flux (in the case of generateCountrySimple())
 * or letting the takeUntil operator handle the termination condition (in the case of generateCountryUntil()).
 *
 * These examples demonstrate the flexibility of Flux.generate in generating values based on application state
 * and the ability to control the termination of the Flux using different approaches (completing from within the
 * callback or using operators like takeUntil).
 */
public class FluxGenerateUntil
{
    public static void main(String[] args)
    {
        //generateCountrySimple();
        generateCountryUntil();
    }

    private static void generateCountrySimple()
    {
        // This method demonstrates a simple use case of Flux.generate
        // It generates random country names using the Faker library
        // and emits them until the country "India" is generated
        Flux.generate(synchronousSink -> {
                    String country = Util.faker().country().name(); // Generate a random country name
                    synchronousSink.next(country); // Emit the generated country name
                    if(country.equalsIgnoreCase("India")) // Check if the generated country is "India"
                    {
                        synchronousSink.complete(); // If it is, complete the Flux
                    }
                })
                .subscribe(Util.subscriber()); // Subscribe to the Flux and handle the emissions
    }

    private static void generateCountryUntil()
    {
        // This method demonstrates another use case of Flux.generate
        // It generates random country names using the Faker library
        // and emits them until the country "India" is generated
        // However, it uses the takeUntil operator instead of completing the Flux from within the callback
        Flux.<String>generate(synchronousSink -> {
                    String country = Util.faker().country().name(); // Generate a random country name
                    synchronousSink.next(country); // Emit the generated country name
                })
                .takeUntil(c -> c.equalsIgnoreCase("India")) // Take emissions until "India" is generated
                .subscribe(Util.subscriber()); // Subscribe to the Flux and handle the emissions
    }
}
