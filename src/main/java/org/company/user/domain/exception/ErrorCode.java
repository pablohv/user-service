package org.company.user.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    EMPLOYEE_EMAIL_ALREADY_EXISTS(HttpStatus.UNPROCESSABLE_CONTENT),
    EMPLOYEE_INVALID_CREDENTIALS(HttpStatus.UNPROCESSABLE_CONTENT);

    private final HttpStatus status;

}
