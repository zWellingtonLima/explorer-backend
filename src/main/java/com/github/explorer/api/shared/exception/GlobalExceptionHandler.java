package com.github.explorer.api.shared.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private static final String ERROR_TYPE_BASE = "https://explorer.dev/errors/";

	@ExceptionHandler(NotFoundException.class)
	public ProblemDetail handleNotFound(NotFoundException ex) {
		return toProblemDetail(HttpStatus.NOT_FOUND, ex);
	}

	@ExceptionHandler(ConflictException.class)
	public ProblemDetail handleConflict(ConflictException ex) {
		return toProblemDetail(HttpStatus.CONFLICT, ex);
	}

	@ExceptionHandler(BusinessRuleException.class)
	public ProblemDetail handleBusinessRule(BusinessRuleException ex) {
		return toProblemDetail(HttpStatus.UNPROCESSABLE_CONTENT, ex);
	}

	@ExceptionHandler(ForbiddenException.class)
	public ProblemDetail handleForbidden(ForbiddenException ex) {
		return toProblemDetail(HttpStatus.FORBIDDEN, ex);
	}

	@ExceptionHandler(Exception.class)
	public ProblemDetail handleUnexpected(Exception ex) {
		log.error("Unhandled exception", ex);
		ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
				"An unexpected error occurred");
		pd.setProperty("code", "INTERNAL_ERROR");
		return pd;
	}

	private ProblemDetail toProblemDetail(HttpStatus status, DomainException ex) {
		ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
		pd.setType(URI.create(ERROR_TYPE_BASE + toSlug(ex.getCode())));
		pd.setProperty("code", ex.getCode());
		pd.setProperty("params", ex.getParams());
		return pd;
	}

	private static String toSlug(String code) {
		return code.toLowerCase(Locale.ROOT).replace("_", "-");
	}

}
