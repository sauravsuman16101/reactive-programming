package io.learn.reactiveprogramming.fileservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Implementation of the FileService interface providing reactive file operations.
 */
public class FileServiceImpl implements FileService
{
    // Logger for this class
    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    // Path to the directory where files will be read from and written to
    public static final Path filePath = Path.of("src/main/resources/"); // Replace with the actual file path

    /**
     * Reads the content of a file reactively.
     *
     * @param fileName The name of the file to read
     * @return A Mono emitting the content of the file as a String
     */
    @Override
    public Mono<String> read(String fileName)
    {
        return Mono.fromCallable(() -> Files.readString(filePath.resolve(fileName)));
    }

    /**
     * Writes content to a file reactively.
     *
     * @param fileName The name of the file to write to
     * @param content The content to write to the file
     * @return A Mono that completes when the write operation is done
     */
    @Override
    public Mono<Void> write(String fileName, String content) {
        return Mono.fromRunnable(() -> writeToFile(fileName, content));
    }

    /**
     * Deletes a file reactively.
     *
     * @param fileName The name of the file to delete
     * @return A Mono that completes when the delete operation is done
     */
    @Override
    public Mono<Void> delete(String fileName) {
        return Mono.fromRunnable(() -> deleteFile(fileName));
    }

    /**
     * Helper method to write content to a file.
     *
     * @param fileName The name of the file to write to
     * @param content The content to write to the file
     * @throws RuntimeException if an IOException occurs during the write operation
     */
    private void writeToFile(String fileName, String content)
    {
        try
        {
            Files.writeString(filePath.resolve(fileName), content);
            log.info("File written: {}", fileName);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Helper method to delete a file.
     *
     * @param fileName The name of the file to delete
     * @throws RuntimeException if an IOException occurs during the delete operation
     */
    private void deleteFile(String fileName)
    {
        try
        {
            Files.delete(filePath.resolve(fileName));
            log.info("File deleted: {}", fileName);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
