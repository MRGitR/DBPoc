package com.infy.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ErrorHandler {

	public void configureErrorHandler(RouteBuilder routeBuilder) {
            routeBuilder.onException(Exception.class)
            .log("Error occurred: ${exception.message}")
            .log("Exception details: ${exception.stacktrace}")
            .handled(true);
            //.setBody(simple("Error occurred"));
    }
	
}
