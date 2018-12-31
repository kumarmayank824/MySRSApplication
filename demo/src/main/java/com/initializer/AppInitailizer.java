package com.initializer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@ComponentScan(basePackages="com")
@EntityScan("com.domain")
@EnableJpaRepositories("com.repository") 
public class AppInitailizer{
    
	public static void main(String[] args) {
		SpringApplication.run(AppInitailizer.class, args);
	}
}
