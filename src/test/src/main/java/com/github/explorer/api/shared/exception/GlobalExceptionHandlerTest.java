package com.github.explorer.api.shared.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

	private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

	@Test
	void conflictException_returns409WithCodeAndParams() {
		// Arrange
		var ex = new ConflictException("EMAIL_TAKEN", "Email already exists", Map.of("email", "test@test.com"));

		// Act
		ProblemDetail pd = handler.handleConflict(ex);

		// Assert
		assertThat(pd.getStatus()).isEqualTo(409);
		assertThat(pd.getDetail()).isEqualTo("Email already exists");
		assertThat(pd.getProperties()).containsEntry("code", "EMAIL_TAKEN");
		assertThat(pd.getProperties()).containsEntry("params", Map.of("email", "test@test.com"));
	}

	@Test
	void notFoundException_returns404WithCodeAndParams() {
		var ex = new NotFoundException("EMAIL_NOT_FOUND_TAKEN", "Email not found",
				Map.of("email", "notFound@email.com"));
		ProblemDetail pd = handler.handleNotFound(ex);
		assertThat(pd.getStatus()).isEqualTo(404);
		assertThat(pd.getDetail()).isEqualTo("Email not found");
		assertThat(pd.getProperties()).containsEntry("code", "EMAIL_NOT_FOUND_TAKEN");
		assertThat(pd.getProperties()).containsEntry("params", Map.of("email", "notFound@email.com"));
	}

	@Test
	void businessRuleException_returns422WithCodeAndParams() {
		var ex = new BusinessRuleException("EMAIL_TAKEN", "Email already exists", Map.of());
		ProblemDetail pd = handler.handleBusinessRule(ex);
		assertThat(pd.getStatus()).isEqualTo(422);
		assertThat(pd.getDetail()).isEqualTo("Email already exists");
		assertThat(pd.getProperties()).containsEntry("code", "EMAIL_TAKEN");
		assertThat(pd.getProperties()).containsEntry("params", Map.of());
	}

	@Test
	void forbiddenException_returns403WithCodeAndParams() {
		var ex = new ForbiddenException("NOT_ALLOWED", "Not allowed", Map.of());
		ProblemDetail pd = handler.handleForbidden(ex);
		assertThat(pd.getStatus()).isEqualTo(403);
		assertThat(pd.getDetail()).isEqualTo("Not allowed");
		assertThat(pd.getProperties()).containsEntry("code", "NOT_ALLOWED");
		assertThat(pd.getProperties()).containsEntry("params", Map.of());
	}

}
