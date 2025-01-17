package com.infy.config;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RestConfiguration {
	
    public void configureRest(RouteBuilder routeBuilder) {
        routeBuilder.restConfiguration()
            .component("http")  // Can be "jetty" if you're using Jetty
            .host("localhost")   // Define the host where your REST service is running
            .port(8081);         // Specify the port
    }
}
