package com.infy.validators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

public class ValidationBeanTest {
	
	@Mock
    private Validator mockValidator;

    @InjectMocks
    private ValidationBean validationBean;

    @BeforeEach
    public void setUp() {
        // Initialize mocks and the ValidationBean instance
        // Use a Validator instance for testing
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        validationBean = new ValidationBean();
        validationBean.setValidator(validator);
    }
    
    @Test
    public void testValidateMessage_invalidMessageFormat() {
        // Sample invalid message text (incorrect number of fields)
        String messageText = "AT|12345|Mark Imaginary|ATZ|1000|USD|Shipping|IN";

        // Expectation: Invalid format exception should be thrown
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validationBean.validateMessage(messageText);
        });
        assertEquals("Message format is invalid.", exception.getMessage());
    }

    @Test
    public void testValidateMessage_validMessage() {
        // Sample valid message text
        String messageText = "AT|1234567891234567891234|Mark Imaginary|ATZ|1000|USD|Shipping|IN|Ship dual FERT chem";

        // Expectation: business logic should return "Suspicious shipment"
        String result = validationBean.validateMessage(messageText);
        assertEquals("Suspicious shipment for TRN 1234567891234567891234", result);
    }

    @Test
    public void testValidateMessage_invalidName() {
        // Sample invalid message text with invalid name
        String messageText = "AT|12345|Invalid Name|ATZ|1000|USD|Shipping|IN|Ship dual FERT chem";

        // Expectation: validation should fail for the name
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validationBean.validateMessage(messageText);
        });
        assertTrue(exception.getMessage().contains("Validation failed for the following fields"));
    }
    
    @Test
    public void testPerformBusinessLogicValidation_allOkay() {
        // Sample message with valid data
        Message message = new Message();
        message.setName("Mark Imaginary");
        message.setRequestType("AT");
        message.setFormatType("ATZ");
        message.setMainMessage("All okay");
        
     // Use reflection to access the private method
        Method method;
		try {
			method = ValidationBean.class.getDeclaredMethod("performBusinessLogicValidation", Message.class);
			method.setAccessible(true); // Make the private method accessible
	        // Invoke the method
	        String result = (String) method.invoke(validationBean, message);
	        assertEquals("All okay TRN null", result);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
        // Expectation: Should return "All okay" message
        //String result = validationBean.performBusinessLogicValidation(message);
        //assertEquals("All okay TRN null", result);  // TRN is null in this case
    }

    @Test
    public void testPerformBusinessLogicValidation_suspicious() {
        // Sample message with suspicious conditions
        Message message = new Message();
        message.setName("Mark Imaginary");
        message.setRequestType("AT");
        message.setFormatType("ATZ");
        message.setMainMessage("Ship dual FERT chem");
        message.setTrn("12345");

        
        // Use reflection to access the private method
        Method method;
		try {
			method = ValidationBean.class.getDeclaredMethod("performBusinessLogicValidation", Message.class);
			method.setAccessible(true); // Make the private method accessible
	        // Invoke the method
	        String result = (String) method.invoke(validationBean, message);
	        assertEquals("Suspicious shipment for TRN 12345", result);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
            
        
        // Expectation: Suspicious shipment
        //String result = validationBean.performBusinessLogicValidation(message);
        //assertEquals("Suspicious shipment for TRN 12345", result);
    }
}
