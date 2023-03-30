package com.example.demosfw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication(scanBasePackages="com.example.demosfw")
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 600)
public class DemoSfwApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSfwApplication.class, args);
	}

}
