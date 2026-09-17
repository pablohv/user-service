package org.company.user.application;

import org.company.user.application.port.out.EmployeeRepository;
import org.company.user.application.port.out.PasswordHash;
import org.company.user.domain.exception.EmployeeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.company.user.factorymodel.ModelFactory.createEmployee;
import static org.company.user.factorymodel.ModelFactory.createEmployeeValidation;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PasswordHash passwordHash;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void whenCreateEmployeeThenReturnSuccess() {
        when(passwordHash.hash(anyString())).thenReturn("password");
        doNothing().when(employeeRepository).saveEmployee(any());

        employeeService.create(createEmployee());
        verify(employeeRepository, times(1)).saveEmployee(any());
    }

    @Test
    void validateEmployeeWhenPasswordIsOk() {
        when(employeeRepository.findByEmail(anyString())).thenReturn(createEmployee());
        when(passwordHash.checkPassword(anyString(), anyString())).thenReturn(true);

        employeeService.validateEmployee(createEmployeeValidation());
    }

    @Test
    void validateEmployeeWhenPasswordIsWrong() {
        when(employeeRepository.findByEmail(anyString())).thenReturn(createEmployee());
        when(passwordHash.checkPassword(anyString(), anyString())).thenReturn(false);

        assertThrows(EmployeeException.class, () -> employeeService.validateEmployee(createEmployeeValidation()));
    }

}