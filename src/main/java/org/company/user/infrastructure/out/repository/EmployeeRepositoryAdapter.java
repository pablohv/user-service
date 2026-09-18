package org.company.user.infrastructure.out.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.user.application.port.out.EmployeeRepository;
import org.company.user.domain.exception.EmployeeException;
import org.company.user.domain.exception.ErrorCode;
import org.company.user.domain.model.Employee;
import org.company.user.infrastructure.out.repository.mapper.EmployeeEntityMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmployeeRepositoryAdapter implements EmployeeRepository {

    private final EmployeeJpaRepository employeeJpaRepository;
    private final EmployeeEntityMapper employeeEntityMapper;

    @Override
    public void saveEmployee(final Employee employee) {
        try {
            employeeJpaRepository.save(employeeEntityMapper.employeeToEntity(employee));
        } catch (DataIntegrityViolationException ex) {
            log.warn("Attempt to register an already existing email");
            throw new EmployeeException(ErrorCode.EMPLOYEE_EMAIL_ALREADY_EXISTS, ex);
        }
    }

    @Override
    public Optional<Employee> findByEmail(final String email) {
        return employeeJpaRepository.findByEmail(email)
                .map(employeeEntityMapper::entityToEmployee);
    }

}
