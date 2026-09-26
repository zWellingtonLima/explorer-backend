package com.github.explorer.api.shared.exception;

import java.util.Map;

public class NotFoundException extends DomainException {

	public NotFoundException(String code, String message, Map<String, Object> params) {
		super(code, message, params);
	}

	public NotFoundException(String code, String message) {
		super(code, message);
	}

}
