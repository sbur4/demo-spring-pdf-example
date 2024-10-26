package org.example.util;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
@UtilityClass
public class FileUtils {
    public static boolean doesPdfExist(final @NonNull String filePath) {
        Path path = Paths.get(filePath);
        return Files.exists(path) && !Files.isDirectory(path);
    }

    public static void savePdf(final ByteArrayOutputStream outputStream, final String outputPath) {
        try {
            Files.write(Paths.get(outputPath), outputStream.toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            log.info("PDF file has been successfully saved to: '{}'", outputPath);
        } catch (IOException ex) {
            log.debug("Failed to save the PDF file: ", ex);
            throw new RuntimeException("Failed to save the PDF file", ex);
        }
    }

    @SneakyThrows
    public void deletePdf(final @NonNull String filePath) {
        Files.deleteIfExists(Paths.get(filePath));
    }
}