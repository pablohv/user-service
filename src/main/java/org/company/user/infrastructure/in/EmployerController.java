package org.company.user.infrastructure.in;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.company.user.application.port.in.EmployeeUseCasePort;
import org.company.user.infrastructure.in.mapper.EmployeeInMapper;
import org.company.user.infrastructure.in.request.EmployeeValidateRequest;
import org.company.user.infrastructure.in.request.EmployerRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployerController {

    private final EmployeeUseCasePort employeeUseCasePort;
    private final EmployeeInMapper employeeInMapper;

    @RequestMapping(
            value = "/registry",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> saveEmployee(@RequestBody @Valid EmployerRequest employerRequest) {
        employeeUseCasePort.create(employeeInMapper.toDomain(employerRequest));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            value = "/validate",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> validateEmployee(@RequestBody @Valid EmployeeValidateRequest employeeValidateRequest) {
        boolean valid = employeeUseCasePort.validateEmployee(employeeInMapper.toDomain(employeeValidateRequest));
        if (!valid)
            return ResponseEntity.unprocessableContent().build();

        return ResponseEntity.ok(true);
    }

}
