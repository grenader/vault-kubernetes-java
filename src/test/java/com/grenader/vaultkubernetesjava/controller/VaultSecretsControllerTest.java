package com.grenader.vaultkubernetesjava.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static com.grenader.vaultkubernetesjava.controller.VaultSecretsController.SECRET_CONFIG_FILENAME;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "secrets.location=/tmp" })
public class VaultSecretsControllerTest {
    public static final String SECRET_DATA = "Hello World";

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${secrets.location}")
    private String basePath;

    @BeforeEach
    public void createFakeSecrets() throws FileNotFoundException {
        System.out.println("basePath = " + basePath);
        try (PrintWriter writer = new PrintWriter(
                new File(basePath+File.separator+ SECRET_CONFIG_FILENAME))) {
            writer.println(SECRET_DATA);
        }

        try (PrintWriter writer = new PrintWriter(
                new File(basePath+File.separator+ "secret-data.txt"))) {
            writer.println(SECRET_DATA+"2");
        }
    }

    @Test
    public void secretURLShouldReturnPresetValue() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/secret",
                String.class)).contains(SECRET_DATA);
    }

    @Test
    public void customSecretURLShouldReturnPresetValue() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/secret/secret-data.txt",
                String.class)).contains(SECRET_DATA+"2");
    }
}