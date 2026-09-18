package org.company.user.infrastructure.out.repository;

import org.company.user.domain.exception.EmployeeException;
import org.company.user.domain.exception.ErrorCode;
import org.company.user.domain.model.Employee;
import org.company.user.infrastructure.out.repository.entity.EmployeeEntity;
import org.company.user.infrastructure.out.repository.mapper.EmployeeEntityMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.company.user.factorymodel.ModelFactory.createEmployee;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeRepositoryAdapterTest {

    @Mock
    private EmployeeJpaRepository employeeJpaRepository;

    @Mock
    private EmployeeEntityMapper employeeEntityMapper;

    @InjectMocks
    private EmployeeRepositoryAdapter employeeRepositoryAdapter;

    @Test
    void saveEmployeeEntityWhenIsOk() {
        when(employeeEntityMapper.employeeToEntity(any())).thenReturn(EmployeeEntity.builder().build());

        employeeRepositoryAdapter.saveEmployee(createEmployee());
        verify(employeeJpaRepository, times(1)).save(any());
    }

    @Test
    void saveEmployeeEntityWhenIsNotOk() {
        when(employeeEntityMapper.employeeToEntity(any())).thenReturn(EmployeeEntity.builder().build());
        when(employeeJpaRepository.save(any())).thenThrow(new DataIntegrityViolationException("DataIntegrityViolationException"));

        EmployeeException employeeException = assertThrows(EmployeeException.class, () -> employeeRepositoryAdapter.saveEmployee(createEmployee()));
        assertEquals(ErrorCode.EMPLOYEE_EMAIL_ALREADY_EXISTS, employeeException.getErrorCode());
        verify(employeeJpaRepository, times(1)).save(any());
    }

    @Test
    void findEmployeeEntityWhenIsOk() {
        when(employeeJpaRepository.findByEmail(anyString())).thenReturn(Optional.of(EmployeeEntity.builder().build()));
        when(employeeEntityMapper.entityToEmployee(any())).thenReturn(createEmployee());

        final Optional<Employee> result = employeeRepositoryAdapter.findByEmail("juan.perez@email.com");

        assertTrue(result.isPresent());
        verify(employeeJpaRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void findEmployeeEntityWhenEmailDoesNotExist() {
        when(employeeJpaRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        final Optional<Employee> result = employeeRepositoryAdapter.findByEmail("unknown@email.com");

        assertTrue(result.isEmpty());
        verify(employeeEntityMapper, never()).entityToEmployee(any());
    }

}
