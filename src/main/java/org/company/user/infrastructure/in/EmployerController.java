package org.company.user.infrastructure.in;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.company.user.application.port.in.EmployeeUseCasePort;
import org.company.user.infrastructure.in.mapper.EmployeeInMapper;
import org.company.user.infrastructure.in.request.EmployeeCredentialsRequest;
import org.company.user.infrastructure.in.request.EmployerRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployerController {

    private final EmployeeUseCasePort employeeUseCasePort;
    private final EmployeeInMapper employeeInMapper;

    @PostMapping(
            value = "/registry",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> saveEmployee(@RequestBody @Valid final EmployerRequest employerRequest) {
        employeeUseCasePort.create(employeeInMapper.toDomain(employerRequest));
        return ResponseEntity.ok().build();
    }

    @PostMapping(
            value = "/validate",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> validateEmployee(@RequestBody @Valid final EmployeeCredentialsRequest employeeCredentialsRequest) {
        employeeUseCasePort.validateEmployee(employeeInMapper.toDomain(employeeCredentialsRequest));
        return ResponseEntity.ok().build();
    }

}
