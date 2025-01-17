package com.infy.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.infy.config.ErrorHandler;
import com.infy.config.RestConfiguration;
import com.infy.validators.ValidationBean;

@Component
public class POCRouteBuilder1 extends RouteBuilder{

	@Autowired
	private ValidationBean validationBean;
	
	@Autowired
	private ErrorHandler errorHandler;

	@Autowired
	private RestConfiguration restConfiguration;	
	
	@Override
	public void configure() throws Exception {

        // Configure global exception handling
        errorHandler.configureErrorHandler(this);

        // Configure REST settings
        restConfiguration.configureRest(this);

        // Main Camel route		
		
		from("activemq:queue:myQueue")
		.errorHandler(deadLetterChannel("log:dead?level=ERROR").maximumRedeliveries(3).redeliveryDelay(1000))
		.log("${body}")
		.bean(validationBean, "validateMessage")
		//.log("----- > ${body}")
        .choice()
            .when(simple("${body}").contains("Suspicious shipment"))
                .log("Sending warning: Suspicious shipment")
                .to("rest:post:/suspiciousShipment")
                //.setBody(simple("Suspicious shipment detected"))
            .otherwise()
                .log("Nothing found, all okay")
                //.setBody(simple("All clear, no issues"))
                //.to("rest:post:/allClear")
        .end();
		
	}

	
	/*	onException(Exception.class)
        .log("Error occurred: ${exception.message}")
        .handled(true)
        .setBody(simple("Error occurred"));
		
		
		 restConfiguration()
         .component("http")  // Use "jetty" if you're using Jetty, or "http" for HTTP component
         .host("localhost")   // Define the host where your REST service is running
         .port(8081);          // Specify the port
         //.bindingMode(RestBindingMode.json); // Choose the binding mode (JSON or XML)

*/
	
}
