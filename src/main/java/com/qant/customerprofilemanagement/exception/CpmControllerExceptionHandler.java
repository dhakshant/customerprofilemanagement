package com.qant.customerprofilemanagement.exception;

import static net.logstash.logback.marker.Markers.append;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.qant.customerprofilemanagement.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class CpmControllerExceptionHandler {

	@ResponseBody
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleApplicationException(
			final CustomerNotFoundException anfe) {
		log.error("In CustomerNotFoundException handler");
		return ResponseEntity.status(ErrorCodes.CUSTOMER_NOT_FOUND.getHttpStatus())
				.body(buildError(ErrorCodes.CUSTOMER_NOT_FOUND,
						ErrorCodes.CUSTOMER_NOT_FOUND.getErrorMessage()));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleTypeMatchException(
			final MethodArgumentTypeMismatchException exception) {
		return ResponseEntity.status(ErrorCodes.BAD_REQUEST.getHttpStatus())
				.body(buildError(ErrorCodes.BAD_REQUEST, exception.getMessage()));
	}

	@ResponseBody
	@ExceptionHandler(value = { ConstraintViolationException.class })
	public ResponseEntity<ErrorResponse> handleApplicationException(
			final ConstraintViolationException cve) {
		log.error("In ConstraintViolationException exception handler");
		final String errorMessage = cve.getConstraintViolations().stream()
				.peek(violation -> log.error(
						violation.getRootBeanClass().getSimpleName()
						.concat(violation.getMessage())))
				.map(ConstraintViolation::getMessage).collect(Collectors.joining(", "));
		return ResponseEntity.status(ErrorCodes.BAD_REQUEST.getHttpStatus())
				.body(buildError(ErrorCodes.BAD_REQUEST, errorMessage));
	}

	protected ErrorResponse buildError(final ErrorCodes errorCodes, final String errorDescription) {
		final ErrorResponse errorResponse = new ErrorResponse().errorCode(errorCodes.getErrorCode())
				.errorName(errorCodes.name()).errorMessage(errorCodes.getErrorMessage())
				.errorDescription(errorDescription);
		log.error(append("exception", errorResponse), null);
		return errorResponse;
	}

}