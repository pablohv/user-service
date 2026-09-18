package org.company.user.application.port.in;

import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeCredentials;

public interface EmployeeUseCasePort {

    void create(Employee employee);

    void validateEmployee(EmployeeCredentials employeeCredentials);

}
