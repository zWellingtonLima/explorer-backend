package com.github.explorer.api.shared.exception;

import java.util.Map;

public class BusinessRuleException extends DomainException {

	public BusinessRuleException(String code, String message, Map<String, Object> params) {
		super(code, message, params);
	}

	public BusinessRuleException(String code, String message) {
		super(code, message);
	}

}
