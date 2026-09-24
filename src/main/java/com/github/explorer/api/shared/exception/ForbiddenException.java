package com.github.explorer.api.shared.exception;

import java.util.Map;

public class ForbiddenException extends DomainException {
    public ForbiddenException(String code, String message, Map<String, Object> params) {
        super(code, message, params);
    }

    public ForbiddenException(String code, String message) {
        super(code, message);
    }
}
