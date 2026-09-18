package org.company.user.application;

import org.company.user.application.port.out.EmployeeRepository;
import org.company.user.application.port.out.PasswordHash;
import org.company.user.domain.exception.EmployeeException;
import org.company.user.domain.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.company.user.factorymodel.ModelFactory.createEmployee;
import static org.company.user.factorymodel.ModelFactory.createEmployeeCredentials;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(createEmployee()));
        when(passwordHash.checkPassword(anyString(), anyString())).thenReturn(true);

        employeeService.validateEmployee(createEmployeeCredentials());
    }

    @Test
    void validateEmployeeWhenPasswordIsWrong() {
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(createEmployee()));
        when(passwordHash.checkPassword(anyString(), anyString())).thenReturn(false);

        assertThrows(EmployeeException.class, () -> employeeService.validateEmployee(createEmployeeCredentials()));
    }

    @Test
    void validateEmployeeWhenEmailDoesNotExist() {
        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        final EmployeeException exception = assertThrows(EmployeeException.class,
                () -> employeeService.validateEmployee(createEmployeeCredentials()));

        assertEquals(ErrorCode.EMPLOYEE_INVALID_CREDENTIALS, exception.getErrorCode());
        verify(passwordHash, never()).checkPassword(anyString(), anyString());
    }

}
