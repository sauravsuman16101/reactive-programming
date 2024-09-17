# Reactive Programming

A comprehensive project demonstrating reactive programming concepts in Java, focusing on non-blocking I/O operations and scalable application design.

## Overview

This project explores advanced reactive programming paradigms and non-blocking I/O using Java and Project Reactor. It showcases how to build efficient, highly scalable applications that can handle high concurrency and process large volumes of data with minimal resource consumption. The project serves as both a learning resource and a practical example of implementing reactive patterns in real-world scenarios.

## Key Features

- Non-blocking I/O operations for improved performance
- Reactive programming patterns using Project Reactor
- Asynchronous and event-driven architecture
- Backpressure handling for robust data flow control
- HTTP client implementations using Project Reactor Netty
- Reactive streams for efficient data processing
- Error handling and resilience patterns in reactive systems

## Project Structure

- `src/main/java/io/learn/reactiveprogramming/`
  - `NonBlockingIO.java`: Demonstrates non-blocking I/O operations
  - `ReactiveStreams.java`: Examples of reactive streams processing
  - `BackpressureHandling.java`: Showcases backpressure handling techniques
  - `common/`: Contains utility classes and common abstractions
    - `Util.java`: Utility methods for the project
    - `AbstractHttpClient.java`: Base class for HTTP clients
    - `ReactiveUtils.java`: Utility methods specific to reactive operations
  - `client/`: Implementations of external service clients
    - `ExternalServiceClient.java`: Client for interacting with external services
    - `ReactiveHttpClient.java`: Reactive HTTP client implementation
  - `handlers/`: Custom handlers for reactive operations
    - `ErrorHandler.java`: Centralized error handling for reactive streams
    - `RetryHandler.java`: Implements retry logic for failed operations
  - `models/`: Domain models and data transfer objects
    - `User.java`: Example domain model for user data
    - `DataEvent.java`: Represents events in the reactive stream
  - `config/`: Configuration classes for the application
    - `ReactorConfig.java`: Configuration for Project Reactor
    - `WebClientConfig.java`: Configuration for WebClient instances
  - `service/`: Business logic and service layer
    - `UserService.java`: Service handling user-related operations
    - `DataProcessingService.java`: Service for processing data streams
  - `repository/`: Data access layer (if applicable)
    - `ReactiveUserRepository.java`: Reactive repository for user data
  - `controller/`: (If using Spring WebFlux) Reactive REST controllers
    - `UserController.java`: Handles user-related HTTP requests
  - `exception/`: Custom exception classes
    - `ReactiveException.java`: Base exception for reactive operations
    - `DataProcessingException.java`: Exception for data processing errors
  - `scheduler/`: Custom schedulers and thread management
    - `CustomReactiveScheduler.java`: Custom scheduler implementation
  - `transformer/`: Reactive transformers and operators
    - `CustomOperators.java`: Custom reactive operators
  - `metrics/`: Metrics and monitoring for reactive streams
    - `ReactiveMetrics.java`: Collects and reports metrics on reactive operations

## Technologies and Dependencies

- Java 22+
- Project Reactor: Core reactive programming library
- Reactor Netty: Non-blocking network application framework
- Spring WebFlux: Reactive web framework (optional, for web components)
- Logback: Logging framework
- JavaFaker: Library for generating fake data (useful for testing and examples)
- JUnit Jupiter: Testing framework
- Reactor Test: Testing utilities for Reactor-based applications
- Mockito: Mocking framework for unit tests

## Getting Started

1. Clone the repository:
   `git clone https://github.com/sauravsuman16101/reactive-programming.git`
2. Navigate to the project directory:
   `cd reactive-programming`
3. Build the project using Maven:
   `mvn clean install`
4. Run the main application or individual examples:
   `java -jar target/reactive-programming-1.0-SNAPSHOT.jar`

## Usage Examples

### Basic Flux Creation and Manipulation

```
Flux.range(1, 5)
 .map(i -> i * 2)
 .subscribe(System.out::println);
```

### Non-blocking I/O Example

```
Mono.fromCallable(() -> {
 // Simulate a blocking operation
 Thread.sleep(1000);
 return "Hello, Reactive World!";
})
.subscribeOn(Schedulers.elastic())
.subscribe(System.out::println);
```

### Backpressure Handling Example

```
Flux.range(1, 10)
.log()
.subscribe(System.out::println);
```

### HTTP Client Example

```
HttpClient client = HttpClient.newHttpClient();

HttpRequest request = HttpRequest.newBuilder()
    .uri(URI.create("https://jsonplaceholder.typicode.com/posts/1"))
    .build();

CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

responseFuture.thenAccept(response -> {
    System.out.println("Response status code: " + response.statusCode());
    System.out.println("Response body: " + response.body());
}).exceptionally(e -> {
    System.err.println("Error occurred: " + e.getMessage());
    return null;
});

// To prevent the program from exiting immediately
responseFuture.join();
```

## Performance Considerations

- Use `subscribeOn()` and `publishOn()` operators judiciously to control thread execution
- Implement backpressure strategies to handle fast producers and slow consumers
- Utilize Project Reactor's `transform()` operator for reusable operator chains

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Acknowledgments

- Project Reactor team for their excellent reactive programming framework

This updated README.md now includes a more detailed project structure, covering all the potential folders and files you might have in your reactive programming project. It provides a comprehensive overview of the project's organization, making it easier for developers to understand the codebase structure and locate specific components.
