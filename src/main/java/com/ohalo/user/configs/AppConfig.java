package com.ohalo.user.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class AppConfig {

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
