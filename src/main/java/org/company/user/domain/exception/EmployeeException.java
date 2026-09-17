package org.company.user.domain.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class EmployeeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final ErrorCode errorCode;

    public EmployeeException(final ErrorCode errorCode, final Throwable cause) {
        super(cause.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public EmployeeException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}
