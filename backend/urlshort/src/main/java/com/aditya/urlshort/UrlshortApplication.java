package com.aditya.urlshort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;

// import com.aditya.urlshort.service.UrlService;

@SpringBootApplication
@EnableScheduling
public class UrlshortApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlshortApplication.class, args);
	}

	// @Bean
	// CommandLineRunner runner(UrlService urlService) {
	// 	return args -> {
	// 		urlService.testRedis();
	// 	};
	// }

}
