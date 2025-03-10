package com.guilhermemaciel.instant_message_app.file;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileUtils {

    private  FileUtils() {}

    /**
     * Reads a file from a given location and returns its content as a byte array.
     *
     * @param fileUrl the file location
     * @return the file content as a byte array
     */
    public static byte[] readFileFromLocation(String fileUrl) {
        if (StringUtils.isBlank(fileUrl)) {
            return new byte[0];
        }
        try {
            Path file = new File(fileUrl).toPath();
            return Files.readAllBytes(file);
        } catch (IOException e) {
            log.warn("No file found at location: {}", fileUrl);
        }
        return new byte[0];
    }
}
