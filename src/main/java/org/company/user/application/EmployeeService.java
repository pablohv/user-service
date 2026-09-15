package org.company.user.application;

import lombok.RequiredArgsConstructor;
import org.company.user.application.port.in.EmployeeUseCasePort;
import org.company.user.application.port.out.EmployeeRepository;
import org.company.user.application.port.out.PasswordHash;
import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeValidation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService implements EmployeeUseCasePort {

    private final EmployeeRepository employeeRepository;
    private final PasswordHash passwordHash;

    @Override
    public void create(Employee employee) {
        String hash = passwordHash.hash(employee.getPassword());
        employee.setPassword(hash);
        employeeRepository.saveEmployee(employee);
    }

    @Override
    public boolean validateEmployee(EmployeeValidation employeeValidate) {
        Employee employee = employeeRepository.findByEmail(employeeValidate.getEmail());
        return passwordHash.checkPassword(employeeValidate.getPassword(), employee.getPassword());
    }

}
