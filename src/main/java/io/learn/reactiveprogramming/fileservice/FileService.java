package io.learn.reactiveprogramming.fileservice;

import reactor.core.publisher.Mono;

/**
 * Interface defining reactive file operations.
 * This service provides methods for reading, writing, and deleting files
 * using Project Reactor's Mono for asynchronous processing.
 */
public interface FileService
{
    /**
     * Reads the content of a file reactively.
     *
     * @param fileName The name of the file to read
     * @return A Mono emitting the content of the file as a String
     */
    Mono<String> read(String fileName);

    /**
     * Writes content to a file reactively.
     *
     * @param fileName The name of the file to write to
     * @param content The content to write to the file
     * @return A Mono that completes when the write operation is done
     */
    Mono<Void> write(String fileName, String content);

    /**
     * Deletes a file reactively.
     *
     * @param fileName The name of the file to delete
     * @return A Mono that completes when the delete operation is done
     */
    Mono<Void> delete(String fileName);

}
