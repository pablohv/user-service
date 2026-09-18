package org.company.user.application.port.out;

import org.company.user.domain.model.Employee;

import java.util.Optional;

public interface EmployeeRepository {

    void saveEmployee(Employee employee);

    Optional<Employee> findByEmail(String email);

}
