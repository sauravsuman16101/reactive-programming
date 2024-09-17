package io.learn.reactiveprogramming;

import io.learn.reactiveprogramming.client.ExternalServiceClient;
import io.learn.reactiveprogramming.common.AbstractHttpClient;
import io.learn.reactiveprogramming.common.Util;

/**
 * NonBlockingIO demonstrates the use of non-blocking I/O operations in reactive programming.
 * This class simulates a scenario where multiple product information requests are made
 * to an external service concurrently, showcasing the benefits of non-blocking operations.
 *
 * Use Case: Concurrent Product Information Retrieval
 * - Simulates retrieving product names for multiple products from an external service.
 * - Demonstrates how non-blocking I/O allows for efficient handling of multiple concurrent requests.
 * - Shows the asynchronous nature of reactive programming in handling I/O-bound operations.
 */
public class NonBlockingIO
{
    /**
     * The main method sets up and executes the non-blocking I/O demonstration.
     * It creates multiple requests for product information and processes them concurrently.
     *
     * @param args Command line arguments (not used in this example)
     */
    public static void main(String[] args)
    {
        // Create a new instance of ExternalServiceClient using the default HTTP client
        var client = new ExternalServiceClient(AbstractHttpClient.createDefaultHttpClient());

        // Loop through product IDs from 1 to 100
        for (int i = 1; i <= 100; i++)
        {
            // Call the getProductName method with the current product ID
            // and subscribe to the resulting Mono<String> using Util.subscriber()
            // Requests are made in sequence but responses are received asynchronously due to non-blocking I/O
            client.getProductName(i).subscribe(Util.subscriber());
        }

        // Wait for 2 seconds to allow asynchronous operations to complete
        Util.sleepSeconds(2);
    }
}

/**
 * Detailed explanation of the Non-Blocking I/O use case:
 *
 * 1. Scenario Setup:
 *    - The class simulates a system that needs to retrieve product names for 100 different products.
 *    - Each product name is fetched from an external service, which typically involves I/O operations.
 *
 * 2. Non-Blocking Nature:
 *    - Traditional blocking I/O would wait for each request to complete before starting the next one.
 *    - This non-blocking approach initiates all requests quickly without waiting for responses.
 *    - Responses are handled asynchronously as they arrive, potentially out of order.
 *
 * 3. ExternalServiceClient Usage:
 *    - An instance of ExternalServiceClient is created to handle communication with the external service.
 *    - The client likely uses reactive programming principles internally to manage requests.
 *
 * 4. Concurrent Request Handling:
 *    - The loop rapidly initiates 100 requests for product names.
 *    - Each call to getProductName() returns a Mono<String>, representing an asynchronous result.
 *    - The subscribe() method is called immediately for each Mono, setting up the processing of the result.
 *
 * 5. Asynchronous Processing:
 *    - The Util.subscriber() method likely sets up a way to handle the response when it arrives.
 *    - This could involve logging the result, storing it, or performing further processing.
 *
 * 6. Efficiency Gains:
 *    - This approach allows for much higher throughput compared to sequential, blocking requests.
 *    - The system can efficiently utilize network resources by having multiple requests in flight simultaneously.
 *
 * 7. Resource Management:
 *    - The non-blocking approach prevents thread blocking, allowing a small number of threads to handle many requests.
 *    - This leads to better resource utilization and scalability.
 *
 * 8. Completion Handling:
 *    - The sleepSeconds(2) call at the end allows time for asynchronous operations to complete.
 *    - In a real-world scenario, you might use more sophisticated methods to wait for or aggregate results.
 *
 * Real-World Applications:
 * - This pattern is particularly useful in scenarios involving multiple API calls, database queries, or file I/O operations.
 * - It's beneficial in microservices architectures where a service might need to aggregate data from multiple sources.
 * - High-traffic web applications can use this to handle many concurrent user requests efficiently.
 *
 * Considerations:
 * - While this approach is highly efficient for I/O-bound operations, it may not provide significant benefits for CPU-bound tasks.
 * - Proper error handling and timeout mechanisms should be implemented in production systems.
 * - Monitoring and controlling the number of concurrent requests is important to prevent overwhelming the external service.
 *
 * This example effectively demonstrates how reactive programming and non-blocking I/O can be used to create
 * highly efficient and scalable systems capable of handling multiple concurrent operations without blocking.
 */
