package com.infy.validators;

import java.util.Set;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.HashSet;

@Component
public class ValidationBean {
	
	  	private Validator validator;
	    private static final Set<String> VALID_NAMES = new HashSet<>(Arrays.asList(
	            "Mark Imaginary", "Govind Real","Shakil Maybe","Chang Imagine" // Add more valid names here
	        ));
	    
	    public ValidationBean() {
	        // Set up the validator
	        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	        this.validator = factory.getValidator();
	    }
	    // Add a setter for the validator
	    public void setValidator(Validator validator) {
	        this.validator = validator;
	    }
	    
	    public String validateMessage(String messageText) {
	        // Step 1: Parse and validate message format
	        String[] fields = parseMessage(messageText);

	        // Step 2: Create Message object
	        Message message = createMessage(fields);
	        //System.out.println("Message -----> "+message);
	        // Step 3: Validate the Message object using Bean Validation
	        validateMessageObject(message);

	        // Step 4: Perform additional business logic validation
	        return performBusinessLogicValidation(message);
	    }
	    
	    private String[] parseMessage(String messageText) {
	        String[] fields = messageText.split("\\|"); // Assuming '|' is the separator
	        if (fields.length != 9) {
	            throw new IllegalArgumentException("Message format is invalid.");
	        }
	        return fields;
	    }
	    
	    private Message createMessage(String[] fields) {
	        Message message = new Message();
	        message.setRequestType(fields[0]);
	        message.setTrn(fields[1]);
	        message.setName(fields[2]);
	        message.setFormatType(fields[3]);
	        message.setAmount(fields[4]);
	        message.setCurrencyCode(fields[5]);
	        message.setService(fields[6]);
	        message.setsCountryCode(fields[7]);
	        message.setMainMessage(fields[8]);
	        return message;
	    }
	    
	    private void validateMessageObject(Message message) {
	        Set<ConstraintViolation<Message>> violations = validator.validate(message);
	        if (!violations.isEmpty()) {
	            StringBuilder errorMsg = new StringBuilder("Validation failed for the following fields:\n");
	            for (ConstraintViolation<Message> violation : violations) {
	                errorMsg.append(violation.getPropertyPath())
	                        .append(": ")
	                        .append(violation.getMessage())
	                        .append("\n");
	            }
	            throw new IllegalArgumentException(errorMsg.toString());
	        }
	    }
	    
	    private String performBusinessLogicValidation(Message message) {
	        if (VALID_NAMES.contains(message.getName()) && "AT".equals(message.getRequestType()) && "ATZ".equals(message.getFormatType()) &&
	            message.getMainMessage().contains("Ship dual FERT chem")) {
	            return "Suspicious shipment for TRN " + message.getTrn();
	        }
	        return "All okay TRN " + message.getTrn();
	    }
	    
	    /*
	    private boolean containsShipDualFertChem(String messageText) {
	        // Check if the text contains "Ship dual FERT chem" after ":32A:"
	        int index = messageText.indexOf(":32A:");
	        if (index != -1) {
	            String subMessage = messageText.substring(index);
	            return subMessage.contains("Ship dual FERT chem");
	        }
	        return false;
	    }*/
}
