package com.infy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PocApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocApplication.class, args);
	}
	
/*	@Bean
    public CamelContext camelContext() throws Exception {
		System.out.println("In context ");
        DefaultCamelContext context = new DefaultCamelContext();
        context.addRoutes(new POCRouteBuilder1());
        return context;
    } */
}
