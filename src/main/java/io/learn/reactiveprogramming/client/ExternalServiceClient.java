package io.learn.reactiveprogramming.client;

import io.learn.reactiveprogramming.common.AbstractHttpClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

/**
 * A client for interacting with an external service to retrieve product information.
 * This client uses reactive programming principles and the Reactor library to make
 * non-blocking HTTP requests and handle the responses asynchronous
*/
public class ExternalServiceClient extends AbstractHttpClient
{
    /**
     * Constructs an instance of the AbstractHttpClient.
     *
     * @param httpClient The HttpClient instance to be used for making HTTP requests.
     */
    public ExternalServiceClient(HttpClient httpClient) {
        super(httpClient);
    }

    /**
     * Sends a GET request to retrieve the name of a product by its ID.
     *
     * @param productId The ID of the product to retrieve the name for.
     * @return A Mono that emits the product name as a String, or an error if the request fails.
     */
    public Mono<String> getProductName(int productId) {
        // Build the URI string using String formatting
        String uri = String.format("/demo01/product/%d", productId);

        // Create a new GET request using a reactive HTTP client
        // Set the URI for the request using the formatted URI string
        // Retrieve the response content (body) as a Flux<DataBuffer>
        // Convert the Flux<DataBuffer> to a Mono<String> by aggregating the DataBuffers into a single String
        // Return the Mono<String> representing the aggregated response body
        return httpClient.get()
                .uri(uri)
                .responseContent()
                .asString()
                .next();
    }

    /**
     * Sends a GET request to a specific URI and returns a Flux<Integer> representing the stream of stock price changes.
     *
     * @return A Flux<Integer> representing the stream of stock price changes
     */
    public Flux<Integer> getStockPriceChanges()
    {
        // Define the URI for the stock price stream
        String uri = "/demo02/stock/stream";

        // Create a Flux by sending a GET request to the specified URI
        return httpClient.get()
                .uri(uri)
                .responseContent() // Get the response content as a Flux<DataBuffer>
                .asString() // Convert the Flux<DataBuffer> to a Flux<String>
                .map(Integer::parseInt); // Convert each String element to an Integer
    }

}
