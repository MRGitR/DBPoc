package com.infy.validators;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Message {

    @NotBlank(message = "Request Type is mandatory and must be 'G'.")
    private String requestType;

    @Size(min = 22, max = 22, message = "TRN must be exactly 22 characters long.")
    @NotBlank(message = "TRN must be exactly 22 characters long.")
    private String trn;

    @NotBlank(message = "Name is mandatory and cannot be empty.")
    //@Size(max = 20, message = "Name should not exceed 20 characters.")
    private String name;

    @NotBlank(message = "Format Type is mandatory and must be 'U'.")
    private String formatType;
    
    @NotBlank(message = "Amount is mandatory.")
    private String amount;
    
    @NotBlank(message = "Currency Code is mandatory.")
    private String currencyCode;

    @NotBlank(message = "Service is mandatory.")
    private String service;
    
    @NotBlank(message = "Service Country code is mandatory.")
    private String sCountryCode;

    public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getsCountryCode() {
		return sCountryCode;
	}

	public void setsCountryCode(String sCountryCode) {
		this.sCountryCode = sCountryCode;
	}

	@NotBlank(message = "Main Message is mandatory.")
    private String mainMessage;

    // Getters and Setters
    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getTrn() {
        return trn;
    }

    public void setTrn(String trn) {
        this.trn = trn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormatType() {
        return formatType;
    }

    public void setFormatType(String formatType) {
        this.formatType = formatType;
    }

    public String getMainMessage() {
        return mainMessage;
    }

    public void setMainMessage(String mainMessage) {
        this.mainMessage = mainMessage;
    }
}
