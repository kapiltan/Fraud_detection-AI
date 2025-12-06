package com.fraudintel.fraudDetection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class FraudIntelApplication {

	public static void main(String[] args) {
		SpringApplication.run(FraudIntelApplication.class, args);
	}

}
