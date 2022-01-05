package com.grenader.vaultkubernetesjava.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileService {

    public String getFileContent(String basePath, String filename) throws IOException {
        Path filePath = Path.of(basePath, filename);
        return Files.readString(filePath);
    }

    public String getFileContent_oldWay(String basePath, String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(
                basePath + File.separator+ filename))) {
            String line = reader.readLine();
            return line;
        }
    }
}
