package org.company.user.infrastructure.out.repository;

import lombok.RequiredArgsConstructor;
import org.company.user.application.port.out.EmployeeRepository;
import org.company.user.domain.exception.EmployeeException;
import org.company.user.domain.exception.ErrorCode;
import org.company.user.domain.model.Employee;
import org.company.user.infrastructure.out.repository.entity.EmployeeEntity;
import org.company.user.infrastructure.out.repository.mapper.EmployeeEntityMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeRepositoryAdapter implements EmployeeRepository {

    private final EmployeJpaRepository employeeJpaRepository;
    private final EmployeeEntityMapper employeeEntityMapper;

    @Override
    public void saveEmployee(Employee employee) {
        try {
            EmployeeEntity employeeEntity = employeeEntityMapper.employeeToEntity(employee);
            employeeJpaRepository.save(employeeEntity);
        } catch (DataIntegrityViolationException ex) {
            throw new EmployeeException(ErrorCode.EMPLOYEE_EMAIL_ALREADY_EXISTS);
        }
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeEntityMapper.entityToEmploy(employeeJpaRepository.findByEmail(email));
    }

}
