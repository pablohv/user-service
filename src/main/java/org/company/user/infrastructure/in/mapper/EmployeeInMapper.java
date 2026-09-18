package org.company.user.infrastructure.in.mapper;

import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeValidation;
import org.company.user.infrastructure.in.request.EmployeeValidateRequest;
import org.company.user.infrastructure.in.request.EmployerRequest;
import org.springframework.stereotype.Component;

@Component
public class EmployeeInMapper {

    public Employee toDomain(final EmployerRequest employeeRequest) {
        final Employee employee = new Employee();

        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setEmail(employeeRequest.getEmail());
        employee.setPassword(employeeRequest.getPassword());
        employee.setPhone(employeeRequest.getPhone());
        employee.setState(employeeRequest.getState());
        employee.setCity(employeeRequest.getCity());
        employee.setCp(employeeRequest.getCp());
        employee.setAddress(employeeRequest.getAddress());
        employee.setCountry(employeeRequest.getCountry());
        employee.setNationality(employeeRequest.getNationality());

        return employee;
    }

    public EmployeeValidation toDomain(final EmployeeValidateRequest employeeValidateRequest) {
        final EmployeeValidation employee = new EmployeeValidation();

        employee.setEmail(employeeValidateRequest.getEmail());
        employee.setPassword(employeeValidateRequest.getPassword());

        return employee;
    }

}
