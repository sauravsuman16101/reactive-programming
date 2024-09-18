package io.learn.reactiveprogramming.fileserviceflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * An implementation of the FileReaderService interface that reads the contents of a file
 * and emits them as a Flux of strings using the Flux.generate operator.
 *
 * This implementation uses a BufferedReader to read the file line by line and emits each
 * line as a separate element in the Flux. The Flux.generate operator is used to generate
 * the elements on-demand, allowing for efficient and scalable file reading.
 *
 * The class also demonstrates the use of the SynchronousSink callback provided by Flux.generate,
 * which allows for emitting values, errors, or completing the stream based on the application's
 * state or the downstream pipeline's requirements.
 */
public class FileReaderServiceImpl implements FileReaderService
{
    private static final Logger log = LoggerFactory.getLogger(FileReaderServiceImpl.class);

    @Override
    public Flux<String> readFile(Path path)
    {
        // Flux.generate is used to generate values on-demand while maintaining state
        return Flux.generate(
                () -> openFile(path), // Initialize the state by opening the file
                this::read, // The callback function to read the file and emit lines
                this::closeFile // The callback function to close the file when the Flux is terminated
        );
    }

    /**
     * Opens the file specified by the given Path and returns a BufferedReader.
     *
     * @param path The Path representing the file to be opened.
     * @return A BufferedReader for reading the file.
     * @throws IOException If an I/O error occurs while opening the file.
     */
    private BufferedReader openFile(Path path) throws IOException
    {
        log.info("Opening file: {}", path);
        return Files.newBufferedReader(path);
    }

    /**
     * Reads the file line by line using the provided BufferedReader and emits each line
     * through the SynchronousSink.
     *
     * @param reader The BufferedReader for reading the file.
     * @param sink The SynchronousSink for emitting values, errors, or completing the stream.
     * @return The BufferedReader for the next iteration of the Flux.generate callback.
     */
    private BufferedReader read(BufferedReader reader, SynchronousSink<String> sink)
    {
        log.info("Reading file");
        try
        {
            String line = reader.readLine();
            if(Objects.isNull(line))
            {
                sink.complete();
            }
            sink.next(line);
        }
        catch (Exception e)
        {
            sink.error(e);
        }
        return reader;
    }

    /**
     * Closes the BufferedReader when the Flux is terminated.
     *
     * @param reader The BufferedReader to be closed.
     */
    private void closeFile(BufferedReader reader)
    {
        log.info("Closing file");
        try
        {
            reader.close();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
