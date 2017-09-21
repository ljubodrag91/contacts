package com.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.config.ElasticsearchConfig;
import com.controller.ContactController;
import com.services.ContactService;


@SpringBootApplication
@ComponentScan(basePackageClasses = {ContactService.class,ElasticsearchConfig.class,ContactController.class})
public class SpringBootFrameworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootFrameworkApplication.class, args);
	}
}
