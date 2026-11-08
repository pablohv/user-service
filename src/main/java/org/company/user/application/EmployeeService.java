package org.company.user.application;

import lombok.RequiredArgsConstructor;
import org.company.user.application.port.in.EmployeeUseCasePort;
import org.company.user.application.port.out.EmployeeRepository;
import org.company.user.application.port.out.PasswordHash;
import org.company.user.domain.exception.EmployeeException;
import org.company.user.domain.exception.ErrorCode;
import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeCredentials;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService implements EmployeeUseCasePort {

    private final EmployeeRepository employeeRepository;
    private final PasswordHash passwordHash;

    @Override
    public void create(final Employee employee) {
        final String hash = passwordHash.hash(employee.password());
        final Employee securedEmployee = employee.withPassword(hash);
        employeeRepository.saveEmployee(securedEmployee);
    }

    @Override
    public void validateEmployee(final EmployeeCredentials employeeValidate) {
        employeeRepository.findByEmail(employeeValidate.email())
                .ifPresentOrElse(employee -> {
                    if (!passwordHash.checkPassword(employeeValidate.password(), employee.password())) {
                        throw new EmployeeException(ErrorCode.EMPLOYEE_INVALID_CREDENTIALS);
                    }
                }, () -> {
                    throw new EmployeeException(ErrorCode.EMPLOYEE_INVALID_CREDENTIALS);
                });
    }

}
