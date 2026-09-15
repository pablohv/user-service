package org.company.user.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EmployeeException extends RuntimeException {

    private final ErrorCode errorCode;

}
