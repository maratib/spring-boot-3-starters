package com.mak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@SpringBootApplication
public class Application {

	@Value("${server.port}")
	private int serverPort;

	@Value("${app.version}")
	private String version;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@GetMapping("/")
	String home() {
		return String.format("API : %s ", version);
	}

	@PostConstruct()
	public void onStart() throws Exception {
		log.info("*** Application started at PORT : " + serverPort);
	}

	@PreDestroy
	public void onExit() {
		log.info("*** Application stopped.");
	}

}
