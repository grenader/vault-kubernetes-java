package com.grenader.vaultkubernetesjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VaultKubernetesJavaApplication {

	public static void main(String[] args) {

		String secretsLocation = System.getenv("SECRETS_LOCATION");
		System.out.println("secretsLocation = " + secretsLocation);

		SpringApplication.run(VaultKubernetesJavaApplication.class, args);
	}

}
