package com.infy.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.apache.camel.component.mock.MockEndpoint.assertIsSatisfied;

@CamelSpringBootTest
@SpringBootTest
public class POCRouteBuilder1Test {

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private ProducerTemplate producerTemplate;

    @Test
    public void testSuspiciousShipmentRoute() throws Exception {
        // Create and configure the mock endpoint
    	String suspiciousMessage = "AT|1234567891234567891234|Mark Imaginary|ATZ|1000|USD|Shipping|IN|Ship dual FERT chem";
        MockEndpoint suspiciousShipmentMock = camelContext.getEndpoint("mock:rest:post:/suspiciousShipment", MockEndpoint.class);
        //MockEndpoint allClearMock = camelContext.getEndpoint("mock:allClear", MockEndpoint.class);

        // Setup expectations for the mock endpoints
        suspiciousShipmentMock.expectedBodiesReceived("Suspicious shipment detected");
        //suspiciousShipmentMock.expectedMessageCount(1);
        //allClearMock.expectedMessageCount(0);

        // Send a message containing "Suspicious shipment" to the route
        producerTemplate.sendBody("activemq:queue:myQueue", suspiciousMessage);

        // Assert that the mock endpoint was hit as expected
        assertIsSatisfied(suspiciousShipmentMock);
    }

    //@Test
    public void testAllClearRoute() throws Exception {
    	String allClearMessage = "AT|1234567891234567891234|Mark Imaginary|ATZ|1000|USD|Shipping|IN|Some other message";
        // Create and configure the mock endpoint
        MockEndpoint suspiciousShipmentMock = camelContext.getEndpoint("mock:suspiciousShipment", MockEndpoint.class);
        MockEndpoint allClearMock = camelContext.getEndpoint("mock:allClear", MockEndpoint.class);

        // Setup expectations for the mock endpoints
        allClearMock.expectedMessageCount(1);
        suspiciousShipmentMock.expectedMessageCount(0);

        // Send a message not containing "Suspicious shipment" to the route
        producerTemplate.sendBody("activemq:queue:myQueue", allClearMessage);

        // Assert that the mock endpoint was hit as expected
        assertIsSatisfied(allClearMock);
    }
}
