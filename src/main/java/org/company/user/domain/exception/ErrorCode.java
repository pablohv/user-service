package org.company.user.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    EMPLOYEE_EMAIL_ALREADY_EXISTS(HttpStatus.UNPROCESSABLE_CONTENT),
    EMPLOYEE_INVALID_CREDENTIALS(HttpStatus.UNPROCESSABLE_CONTENT),
    GENERIC_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR);

    private final HttpStatus status;

}
