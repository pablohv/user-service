package org.company.user.infrastructure.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.user.domain.exception.EmployeeException;
import org.company.user.domain.exception.ErrorCode;
import org.company.user.infrastructure.in.response.ValidationErrorResponse;
import org.company.user.infrastructure.out.MessageProvider;
import org.company.user.infrastructure.util.UtilDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageProvider messageProvider;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
            final MethodArgumentNotValidException ex) {

        final List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> messageProvider.getMessage(error.getDefaultMessage()))
                .toList();

        final ValidationErrorResponse response =
                new ValidationErrorResponse(
                        "VALIDATION_ERROR",
                        errors,
                        UtilDate.getCurrentDate()
                );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<?> handleEmployeeException(final EmployeeException ex) {

        if (log.isWarnEnabled()) {
            log.warn("Business rule violation: {}", ex.getErrorCode().name());
        }

        final String message = messageProvider.getMessage(ex.getErrorCode().name());

        final List<String> errors = new ArrayList<>();
        errors.add(message);

        final ValidationErrorResponse response =
                new ValidationErrorResponse(
                        ex.getErrorCode().name(),
                        errors,
                        UtilDate.getCurrentDate()
                );

        return ResponseEntity
                .status(ex.getErrorCode().getStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ValidationErrorResponse> handleGenericErrors(final Exception ex) {

        log.error("Unhandled exception", ex);

        final String message = messageProvider.getMessage(ErrorCode.GENERIC_EXCEPTION.name());

        final List<String> errors = new ArrayList<>();
        errors.add(message);

        final ValidationErrorResponse response =
                new ValidationErrorResponse(
                        ErrorCode.GENERIC_EXCEPTION.name(),
                        errors,
                        UtilDate.getCurrentDate()
                );

        return ResponseEntity
                .status(ErrorCode.GENERIC_EXCEPTION.getStatus())
                .body(response);
    }

}
