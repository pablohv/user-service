package org.company.user.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.user.application.port.in.EmployeeUseCasePort;
import org.company.user.application.port.out.EmployeeRepository;
import org.company.user.application.port.out.PasswordHash;
import org.company.user.domain.exception.EmployeeException;
import org.company.user.domain.exception.ErrorCode;
import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeCredentials;
import org.springframework.stereotype.Service;

@Slf4j
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
        if (log.isInfoEnabled()) {
            log.info("Employee created with email {}", employee.getEmail());
        }
    }

    @Override
    public void validateEmployee(final EmployeeCredentials employeeCredentials) {
        final Employee employee = employeeRepository.findByEmail(employeeCredentials.getEmail())
                .orElseThrow(() -> new EmployeeException(ErrorCode.EMPLOYEE_INVALID_CREDENTIALS));

        if (!passwordHash.checkPassword(employeeCredentials.getPassword(), employee.getPassword())) {
            if (log.isWarnEnabled()) {
                log.warn("Invalid credentials for email {}", employeeCredentials.getEmail());
            }
            throw new EmployeeException(ErrorCode.EMPLOYEE_INVALID_CREDENTIALS);
        }
    }

}
