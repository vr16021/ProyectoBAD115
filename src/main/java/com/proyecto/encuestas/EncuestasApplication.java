package com.proyecto.encuestas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = { RedisAutoConfiguration.class })
@EnableJpaAuditing
public class EncuestasApplication {

	public static void main(String[] args) {
		SpringApplication.run(EncuestasApplication.class, args);
	}

}
