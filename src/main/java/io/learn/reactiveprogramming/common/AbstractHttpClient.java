package io.learn.reactiveprogramming.common;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.LoopResources;

import java.util.concurrent.TimeUnit;

/**
 * An abstract base class for creating HTTP clients using reactive programming principles.
 * This class provides a foundation for building non-blocking, asynchronous HTTP clients
 * using the Reactor Netty library.
 *
 * HttpClient is a powerful tool for making HTTP requests in a reactive manner. It offers:
 * - Non-blocking I/O operations
 * - Streaming capabilities for large payloads
 * - Support for HTTP/2
 * - Connection pooling
 * - Backpressure handling
 * - Integration with Project Reactor for composing asynchronous operations
 */
public abstract class AbstractHttpClient {

    private static final String BASE_URL = "http://localhost:7070";
    protected final HttpClient httpClient;

    /**
     * Constructs an instance of the AbstractHttpClient.
     *
     * @param httpClient The HttpClient instance to be used for making HTTP requests.
     */
    public AbstractHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Creates a new HttpClient instance with default settings.
     *
     * This method demonstrates how to configure an HttpClient with:
     * - Custom event loop resources for optimal performance
     * - A base URL for all requests
     *
     * Additional configurations could include:
     * - Connection timeouts
     * - Response timeouts
     * - SSL/TLS settings
     * - Proxy settings
     * - Custom headers
     *
     * @return A new HttpClient instance.
     */
    public static HttpClient createDefaultHttpClient() {
        var loopResources = LoopResources.create("http-client", 1, true);
        return HttpClient.create()
                .runOn(loopResources)
                .baseUrl(BASE_URL);
    }

    /**
     * HttpClient can be used for various HTTP operations such as:
     * - GET requests:  httpClient.get().uri("/api/resource").response()
     * - POST requests: httpClient.post().uri("/api/resource").send(bodySender).response()
     * - PUT requests:  httpClient.put().uri("/api/resource").send(bodySender).response()
     * - DELETE requests: httpClient.delete().uri("/api/resource").response()
     *
     * It also supports advanced features like:
     * - WebSocket connections
     * - Server-Sent Events (SSE)
     * - File uploads and downloads
     * - Request/response transformation
     * - Retry and backoff strategies
     */
}
