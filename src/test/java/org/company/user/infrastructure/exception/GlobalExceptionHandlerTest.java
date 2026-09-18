package org.company.user.infrastructure.exception;

import org.company.user.domain.exception.EmployeeException;
import org.company.user.domain.exception.ErrorCode;
import org.company.user.infrastructure.in.response.ValidationErrorResponse;
import org.company.user.infrastructure.out.MessageProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private MessageProvider messageProvider;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void handleEmployeeExceptionReturnsMappedStatusAndMessage() {
        when(messageProvider.getMessage(eq(ErrorCode.EMPLOYEE_INVALID_CREDENTIALS.name())))
                .thenReturn("Invalid credentials");

        final ResponseEntity<?> response =
                globalExceptionHandler.handleEmployeeException(new EmployeeException(ErrorCode.EMPLOYEE_INVALID_CREDENTIALS));

        assertEquals(HttpStatus.UNPROCESSABLE_CONTENT, response.getStatusCode());
        final ValidationErrorResponse body = (ValidationErrorResponse) response.getBody();
        assertEquals(ErrorCode.EMPLOYEE_INVALID_CREDENTIALS.name(), body.code());
        assertEquals("Invalid credentials", body.errors().get(0));
    }

    @Test
    void handleGenericErrorsReturnsInternalServerError() {
        when(messageProvider.getMessage(eq(ErrorCode.GENERIC_EXCEPTION.name())))
                .thenReturn("Unknow error");

        final ResponseEntity<ValidationErrorResponse> response =
                globalExceptionHandler.handleGenericErrors(new RuntimeException("boom"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unknow error", response.getBody().errors().get(0));
    }

}
