package org.company.user.infrastructure.in.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeValidateRequest {

    @NotBlank
    private String email;

    @Size(min = 8, max = 20, message = "VALIDATION_PASSWORD_SIZE")
    private String password;

}
