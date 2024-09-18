package io.learn.reactiveprogramming.fileserviceflux;

import reactor.core.publisher.Flux;

import java.nio.file.Path;

/**
 * The FileReaderService interface defines a contract for reading files and emitting the contents
 * as a Flux of strings.
 *
 * This interface is part of a reactive programming approach to file reading, where the contents
 * of a file are emitted as a stream of strings, allowing for non-blocking and asynchronous processing.
 *
 * Implementations of this interface should provide a method to read a file specified by a Path
 * and return a Flux that emits the contents of the file line by line.
 *
 * The use of Flux allows for efficient and scalable file reading, as the contents are emitted
 * as they are read, without the need to load the entire file into memory at once. This approach
 * is particularly useful for reading large files or processing multiple files concurrently.
 */
public interface FileReaderService
{
    /**
     * Reads the contents of a file specified by the given Path and returns a Flux that emits
     * the contents of the file line by line.
     *
     * @param path The Path representing the file to be read.
     * @return A Flux that emits the contents of the file line by line.
     */
    Flux<String> readFile(Path path);
}
