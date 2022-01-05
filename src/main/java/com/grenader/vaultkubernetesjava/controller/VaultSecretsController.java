package com.grenader.vaultkubernetesjava.controller;

import com.grenader.vaultkubernetesjava.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

@RestController
public class VaultSecretsController {

    public static final String SECRET_CONFIG_FILENAME = "database-config.txt";

    @Autowired
    private FileService fileService;

    @Value("${secrets.location}")
    private String basePath;

    /**
     * Just to check the URLs
     *
     * @return
     */
    @GetMapping("/")
    public String getInfo() {
        return "This application prints a content of a file that supposes to hold a Vault secret received via " +
                "Vault Agent sidecar container.<br><br>"+
                "<a HREF=\"/secret/\">default secret data</a> (database-config.txt)<br>"+
                "<a HREF=\"/secret/<secret-file-name>\">custom secret data</a> (replace &#x3C;secret-file-name&#x3E; with your secret file name)";
    }

    @GetMapping("/ok")
    public String getOk() {
        return "Ok";
    }

    /**
     * Read content of default secret file
     * @return
     */
    @GetMapping("/secret")
    public String getSecretData() throws IOException {
        System.out.println("basePath = " + basePath);
        return fileService.getFileContent(basePath, SECRET_CONFIG_FILENAME);
    }

    /**
     * Read content of a custom secret file
     * @return
     */
    @GetMapping("/secret/{filePath}")
    public String getSecretData(@PathVariable(value="filePath") String filePath) throws IOException {
        System.out.println("basePath = " + basePath);
        return fileService.getFileContent(basePath, filePath);
    }

}
