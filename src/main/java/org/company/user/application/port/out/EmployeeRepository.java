package org.company.user.application.port.out;

import org.company.user.domain.model.Employee;

public interface EmployeeRepository {

    void saveEmployee(Employee employee);

    Employee findByEmail(String email);

}
