package com.infy.ServiceCallers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestServiceMessage {
	
	Logger logger = LoggerFactory.getLogger(RestServiceMessage.class);
	
    @PostMapping("/suspiciousShipment")
    public String handleSuspiciousShipment(@RequestBody String request) {
        // Log incoming suspicious request
    	logger.info("Received suspicious shipment: " + request);
        return "Suspicious shipment detected";
    }
    
    @PostMapping("/allClear")
    public String handleAllClear(@RequestBody String request) {
        // Log incoming all-clear request
        //System.out.println("Received all-clear request: " + request);
        return "All clear, no issues";
    }
}
