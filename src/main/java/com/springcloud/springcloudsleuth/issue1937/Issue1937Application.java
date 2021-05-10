package com.springcloud.springcloudsleuth.issue1937;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:spring-integration-context.xml")
public class Issue1937Application {

	public static void main(String[] args) {
		SpringApplication.run(Issue1937Application.class, args);
	}
}
