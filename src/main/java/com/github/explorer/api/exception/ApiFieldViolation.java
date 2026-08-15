package com.github.explorer.api.exception;

public record ApiFieldViolation(
        String field,
        String message
) {

}
