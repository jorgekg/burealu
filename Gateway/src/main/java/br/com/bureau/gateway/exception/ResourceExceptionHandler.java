package br.com.bureau.gateway.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(),
				HttpStatus.NOT_FOUND.value(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.FORBIDDEN.value(), "User not have authorizartion",
				HttpStatus.FORBIDDEN.value(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {

		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Validation error", -1,
				System.currentTimeMillis());
		for (FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(EmailExistsException.class)
	public ResponseEntity<StandardError> emailExistsException(EmailExistsException e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.CONFLICT.value(), "This e-mail has existis",
				HttpStatus.CONFLICT.value(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<StandardError> zuulException(Exception e, HttpServletRequest request) {

		StandardError err = new StandardError(HttpStatus.GATEWAY_TIMEOUT.value(),
				"This microservice did not respond, check if it is operational, or if the shovel has occurred. (wait 30 seconds after raising the microservice)",
				HttpStatus.GATEWAY_TIMEOUT.value(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(err);
	}

}
