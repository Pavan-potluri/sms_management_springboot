package com.pod5.sms_management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class SmsApplication {

	@Configuration
	public class CorsConfig implements WebMvcConfigurer {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**")
					.allowedOrigins("http://localhost:3000") // Change this to the domain of your React app
					.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
					.allowedHeaders("*")
					.allowCredentials(true);
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(SmsApplication.class, args);
	}

}