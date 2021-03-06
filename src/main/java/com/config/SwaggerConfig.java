package com.config;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.base.Predicate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket contactsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("contact-api")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(paths())
				.build();
	}

	@SuppressWarnings("unchecked")
	private Predicate<String> paths() {
		return or(regex("/.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("iContacts")
				.description("API for contact CRUD operations")
				.contact(new Contact("ljubodrag91@gmail.com", null, null))
				.version("0.3").build();
	}
}
