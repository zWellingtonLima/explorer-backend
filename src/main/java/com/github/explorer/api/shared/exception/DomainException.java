package com.github.explorer.api.shared.exception;

import java.util.Map;

public abstract class DomainException extends RuntimeException {

    private final String code;
    private final Map<String, Object> params;

    protected DomainException(String code, String message, Map<String, Object> params) {
        super(message);
        this.code = code;
        this.params = Map.copyOf(params); // Creates an immutable map. If this was this.params = params it could receive a HashMap that can change over time
    }

    protected DomainException(String code, String message) {
        this(code, message, Map.of());
    }

    public String getCode() {
        return code;
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
