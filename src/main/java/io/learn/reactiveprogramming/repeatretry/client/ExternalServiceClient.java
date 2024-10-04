package io.learn.reactiveprogramming.repeatretry.client;

import io.learn.reactiveprogramming.common.AbstractHttpClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;

public class ExternalServiceClient extends AbstractHttpClient
{
    /**
     * Constructs an instance of the AbstractHttpClient.
     *
     * @param httpClient
     *         The HttpClient instance to be used for making HTTP requests.
     */
    public ExternalServiceClient(HttpClient httpClient)
    {
        super(httpClient);
    }

    public Mono<String> getProductName(int productId)
    {
        return get("/demo06/product/" + productId);
    }

    public Mono<String> getCountry()
    {
        return get("/demo06/country");
    }

    private Mono<String> get(String path)
    {
        return this.httpClient.get()
                .uri(path)
                .response(this::toResponse)
                .next();
    }

    private Flux<String> toResponse(HttpClientResponse httpClientResponse, ByteBufFlux byteBufFlux)
    {
        return switch (httpClientResponse.status().code())
        {
            case 200 -> byteBufFlux.asString();
            case 400 -> Flux.error(new ClientError());
            default -> Flux.error(new ServerError());
        };
    }
}
