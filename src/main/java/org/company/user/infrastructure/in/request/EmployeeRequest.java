package org.company.user.infrastructure.in.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    @NotBlank(message = "VALIDATION_NAME_MUST_NOT_BE_BLANK")
    private String firstName;

    @NotBlank(message = "VALIDATION_LAST_NAME_MUST_NOT_BE_BLANK")
    private String lastName;

    @NotBlank(message = "VALIDATION_EMAIL_MUST_NOT_BE_BLANK")
    private String email;

    @Size(min = 8, max = 20, message = "VALIDATION_PASSWORD_SIZE")
    private String password;

    @Pattern(regexp = "^[0-9]{10}$", message = "VALIDATION_PHONE")
    private String phone;

    private String state;
    private String city;

    @Pattern(regexp = "\\d{5}", message = "VALIDATION_CP")
    private String cp;

    @NotBlank(message = "VALIDATION_ADDRESS_MUST_NOT_BE_BLANK")
    private String address;

    private String country;
    private String nationality;

}
