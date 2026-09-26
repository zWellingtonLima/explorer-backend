package com.github.explorer.api.shared.exception;

import java.util.Map;

public class ConflictException extends DomainException {

	public ConflictException(String code, String message, Map<String, Object> params) {
		super(code, message, params);
	}

	public ConflictException(String code, String message) {
		super(code, message);
	}

}
