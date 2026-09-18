package org.company.user.infrastructure.in;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.company.user.application.port.in.EmployeeUseCasePort;
import org.company.user.infrastructure.in.mapper.EmployeeInMapper;
import org.company.user.infrastructure.in.request.EmployeeCredentialsRequest;
import org.company.user.infrastructure.in.request.EmployeeRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeUseCasePort employeeUseCasePort;
    private final EmployeeInMapper employeeInMapper;

    @RequestMapping(
            value = "/registry",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> saveEmployee(@RequestBody @Valid final EmployeeRequest employeeRequest) {
        employeeUseCasePort.create(employeeInMapper.toDomain(employeeRequest));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(
            value = "/validate",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> validateEmployee(@RequestBody @Valid final EmployeeCredentialsRequest employeeCredentialsRequest) {
        employeeUseCasePort.validateEmployee(employeeInMapper.toDomain(employeeCredentialsRequest));
        return ResponseEntity.ok().build();
    }

}
