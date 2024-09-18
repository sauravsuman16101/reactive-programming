package io.learn.reactiveprogramming.fileserviceflux;

import io.learn.reactiveprogramming.common.Util;

import java.nio.file.Path;

public class FileReaderServiceExecute
{
    public static void main(String[] args)
    {
        Path filePath = Path.of("src/main/resources/FluxFile.txt");
        var fileReaderService = new FileReaderServiceImpl();

        // Use case 1: Read the entire file and subscribe to the Flux
        fileReaderService.readFile(filePath)
                .subscribe(Util.subscriber());

        // Use case 2: Read the first 6 lines of the file
        fileReaderService.readFile(filePath)
                .take(9)
                .subscribe(Util.subscriber());

        // Use case 3: Read lines until a specific line is encountered
        fileReaderService.readFile(filePath)
                .takeUntil(s -> s.equalsIgnoreCase("line99"))
                .subscribe(Util.subscriber());
    }
}

/**
 * The FileReaderServiceExecute class demonstrates three use cases for reading files using
 * the FileReaderService implementation.
 *
 * Use case 1: Read the entire file and subscribe to the Flux
 * In this use case, the readFile method of the FileReaderService is called with the file path,
 * and the resulting Flux is subscribed to using Util.subscriber(). This will emit all lines
 * of the file as they are read.
 *
 * Use case 2: Read the first 6 lines of the file
 * In this use case, the take(6) operator is applied to the Flux returned by the readFile method.
 * This will cause the Flux to emit only the first 6 lines of the file before completing.
 *
 * Use case 3: Read lines until a specific line is encountered
 * In this use case, the takeUntil operator is applied to the Flux returned by the readFile method.
 * The takeUntil operator takes a predicate function as an argument, which is evaluated for each
 * emitted line. When the predicate returns true (in this case, when the line is "line99"), the
 * Flux completes, and no further lines are emitted.
 *
 * These use cases demonstrate the flexibility of the FileReaderService implementation and the
 * power of reactive programming with Flux. By combining the readFile method with various operators
 * like take and takeUntil, different file reading scenarios can be achieved without modifying the
 * core implementation.
 */
