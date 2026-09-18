package org.company.user.infrastructure.in;

import org.company.user.application.port.in.EmployeeUseCasePort;
import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeCredentials;
import org.company.user.infrastructure.in.mapper.EmployeeInMapper;
import org.company.user.infrastructure.in.request.EmployeeCredentialsRequest;
import org.company.user.infrastructure.in.request.EmployeeRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.company.user.factorymodel.ModelFactory.createEmployeeCredentialsRq;
import static org.company.user.factorymodel.ModelFactory.createEmployeeRq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeUseCasePort employeeUseCasePort;

    @Mock
    private EmployeeInMapper employeeInMapper;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void saveEmployeeWhenIsOK() {
        when(employeeInMapper.toDomain(any(EmployeeRequest.class))).thenReturn(new Employee());
        doNothing().when(employeeUseCasePort).create(any(Employee.class));

        employeeController.saveEmployee(createEmployeeRq());
        verify(employeeUseCasePort, times(1)).create(any(Employee.class));
    }

    @Test
    void validateEmployeeWhenIsOK() {
        when(employeeInMapper.toDomain(any(EmployeeCredentialsRequest.class))).thenReturn(new EmployeeCredentials());
        doNothing().when(employeeUseCasePort).validateEmployee(any(EmployeeCredentials.class));

        employeeController.validateEmployee(createEmployeeCredentialsRq());
        verify(employeeUseCasePort, times(1)).validateEmployee(any(EmployeeCredentials.class));
    }

}
