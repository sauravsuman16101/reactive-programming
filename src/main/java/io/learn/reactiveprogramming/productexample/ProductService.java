package io.learn.reactiveprogramming.productexample;

import io.learn.reactiveprogramming.client.ExternalServiceClient;
import io.learn.reactiveprogramming.common.AbstractHttpClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class ProductService
{
    ExternalServiceClient client = new ExternalServiceClient(AbstractHttpClient.createDefaultHttpClient());

    /**
     * Retrieves the product name for a given product ID.
     *
     * @param productId The ID of the product to retrieve.
     * @return A Mono containing the product name.
     */
    public Mono<String> getProductName(int productId)
    {
        var defaultPath = "/demo03/product/" + productId;
        var timeoutPath = "/demo03/timeout-fallback/product/" + productId;
        var emptyPath = "/demo03/empty-fallback/product/" + productId;

        return client
                .getProductNameByPath(defaultPath)
                .timeout(Duration.ofSeconds(2), client.getProductNameByPath(timeoutPath))
                .switchIfEmpty(client.getProductNameByPath(emptyPath));
    }
}
