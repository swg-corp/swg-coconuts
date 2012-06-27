package com.swg.coconuts.action;

public interface ValidationAware extends Validatable {
	void setValidationEnabled(boolean validationEnabled);
	boolean isValidationEnabled();
	
	void addValidator(Validator<?> validator);
	void removeValidator(Validator<?> validator);
}
