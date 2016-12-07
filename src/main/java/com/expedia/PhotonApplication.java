package com.expedia;

import com.expedia.config.PhotonProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.ZonedDateTime;
import java.util.Date;

import static springfox.documentation.builders.PathSelectors.regex;

@SpringBootApplication
@EnableSwagger2
@EnableConfigurationProperties({PhotonProperties.class})
public class PhotonApplication {

	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

	public static void main(String[] args) {
		SpringApplication.run(PhotonApplication.class, args);
	}

	@Bean
	public Docket docketConfig(){
		return new Docket(DocumentationType.SWAGGER_2)
				.forCodeGeneration(true)
				.genericModelSubstitutes(ResponseEntity.class)
				.directModelSubstitute(ZonedDateTime.class, Date.class)
				.select()
				.paths(regex(DEFAULT_INCLUDE_PATTERN))
				.build();
	}
}
