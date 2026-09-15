package org.company.user.application.port.in;

import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeValidation;

public interface EmployeeUseCasePort {

    void create(Employee employee);

    boolean validateEmployee(EmployeeValidation employeeValidate);

}
