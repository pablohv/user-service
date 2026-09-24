package org.company.user.infrastructure.in;

import org.company.user.application.port.in.EmployeeUseCasePort;
import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeValidation;
import org.company.user.infrastructure.in.mapper.EmployeeInMapper;
import org.company.user.infrastructure.in.request.EmployeeValidateRequest;
import org.company.user.infrastructure.in.request.EmployerRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.company.user.factory.model.ModelFactory.createEmployeeValidateRq;
import static org.company.user.factory.model.ModelFactory.createEmployerRq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployerControllerTest {

    @Mock
    private EmployeeUseCasePort employeeUseCasePort;

    @Mock
    private EmployeeInMapper employeeInMapper;

    @InjectMocks
    private EmployerController employerController;

    @Test
    void saveEmployeeWhenIsOK() {
        when(employeeInMapper.toDomain(any(EmployerRequest.class))).thenReturn(new Employee());
        doNothing().when(employeeUseCasePort).create(any(Employee.class));

        employerController.saveEmployee(createEmployerRq());
        verify(employeeUseCasePort, times(1)).create(any(Employee.class));
    }

    @Test
    void validateEmployeeWhenIsOK() {
        when(employeeInMapper.toDomain(any(EmployeeValidateRequest.class))).thenReturn(new EmployeeValidation());
        doNothing().when(employeeUseCasePort).validateEmployee(any(EmployeeValidation.class));

        employerController.validateEmployee(createEmployeeValidateRq());
        verify(employeeUseCasePort, times(1)).validateEmployee(any(EmployeeValidation.class));
    }

}