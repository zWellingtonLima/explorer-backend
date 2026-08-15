package com.github.explorer.api.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ====================================================
    // Validation Form Errors
    // ====================================================
    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException exception) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Requisição Inválida");
        pd.setType(URI.create("urn:explorer:problem:validation"));

        List<ApiFieldViolation> violations = exception.getConstraintViolations()
                .stream()
                .map(violation -> new ApiFieldViolation(
                        getFieldName(violation.getPropertyPath().toString()),
                        violation.getMessage()
                ))
                .toList();

        pd.setProperty("violations", violations);

        return pd;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMissingServletRequestParameter(MissingServletRequestParameterException exception) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Requisição Inválida");
        pd.setType(URI.create("urn:explorer:problem:validation"));

        List<ApiFieldViolation> violations = List.of(
                new ApiFieldViolation(
                        exception.getParameterName(),
                        "Parâmetro obrigatório não enviado."
                ));
        pd.setProperty("violations", violations);

        return pd;
    }

    // ====================================================
    // HELPERS
    // ====================================================
    private String getFieldName(String propertyPath) {
        int lastDot = propertyPath.lastIndexOf('.');
        return propertyPath.substring(lastDot + 1);
    }
}
