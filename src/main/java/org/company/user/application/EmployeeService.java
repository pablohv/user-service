package org.company.user.application;

import lombok.RequiredArgsConstructor;
import org.company.user.application.port.in.EmployeeUseCasePort;
import org.company.user.application.port.out.EmployeeRepository;
import org.company.user.application.port.out.PasswordHash;
import org.company.user.domain.exception.EmployeeException;
import org.company.user.domain.exception.ErrorCode;
import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeValidation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService implements EmployeeUseCasePort {

    private final EmployeeRepository employeeRepository;
    private final PasswordHash passwordHash;

    @Override
    public void create(final Employee employee) {
        final String hash = passwordHash.hash(employee.getPassword());
        employee.setPassword(hash);
        employeeRepository.saveEmployee(employee);
    }

    @Override
    public void validateEmployee(final EmployeeValidation employeeValidate) {
        final Employee employee = employeeRepository.findByEmail(employeeValidate.getEmail());

        if (!passwordHash.checkPassword(employeeValidate.getPassword(), employee.getPassword())) {
            throw new EmployeeException(ErrorCode.EMPLOYEE_INVALID_CREDENTIALS);
        }
    }

}
