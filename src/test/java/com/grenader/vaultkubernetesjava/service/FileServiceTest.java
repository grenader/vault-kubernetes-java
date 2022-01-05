package com.grenader.vaultkubernetesjava.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    private FileService service  = new FileService();

    @Test
    void getFileContent() throws IOException {
        // create a file
        Path filePath = Path.of( "/tmp"+"/"+ "demo.txt");
        String expectedContent  = "hello world !!";
        Files.writeString(filePath, expectedContent);

        // read
        String fileContent = service.getFileContent("/tmp", "demo.txt");

        assertEquals(expectedContent, fileContent);

        Files.delete(filePath);
    }

    @Test
    void getFileContent_fail() {
        assertThrows(NoSuchFileException.class,
                () -> {service.getFileContent("/tmp", "non-existing-file.txt");});
    }
}