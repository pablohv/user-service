package org.company.user.infrastructure.in.response;

import java.util.List;

public record ValidationErrorResponse(
        String code,
        List<String> errors,
        String timestamp
) {

    public ValidationErrorResponse {
        errors = List.copyOf(errors);
    }

}
