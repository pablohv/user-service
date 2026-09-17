package org.company.user.factorymodel;

import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeValidation;
import org.company.user.infrastructure.in.request.EmployeeValidateRequest;
import org.company.user.infrastructure.in.request.EmployerRequest;

public class ModelFactory {

    public static EmployerRequest createEmployerRq() {
        EmployerRequest employee = new EmployerRequest();

        employee.setFirstName("Juan");
        employee.setLastName("Pérez");
        employee.setEmail("juan.perez@email.com");
        employee.setPassword("Password123");
        employee.setPhone("5512345678");

        employee.setState("Ciudad de México");
        employee.setCity("Benito Juárez");
        employee.setCp("03100");
        employee.setAddress("Av. Insurgentes Sur 123");

        employee.setCountry("México");
        employee.setNationality("Mexicana");

        return employee;
    }

    public static EmployeeValidateRequest createEmployeeValidateRq() {
        EmployeeValidateRequest employee = new EmployeeValidateRequest();

        employee.setEmail("juan.perez@email.com");
        employee.setPassword("Password123");

        return employee;
    }

    public static Employee createEmployee() {
        Employee employee = new Employee();

        employee.setFirstName("Juan");
        employee.setLastName("Pérez");
        employee.setEmail("juan.perez@email.com");
        employee.setPassword("Password123");
        employee.setPhone("5512345678");

        employee.setState("Ciudad de México");
        employee.setCity("Benito Juárez");
        employee.setCp("03100");
        employee.setAddress("Av. Insurgentes Sur 123");

        employee.setCountry("México");
        employee.setNationality("Mexicana");

        return employee;
    }

    public static EmployeeValidation createEmployeeValidation() {
        EmployeeValidation employeeValidation = new EmployeeValidation();
        employeeValidation.setEmail("juan.perez@email.com");
        employeeValidation.setPassword("Password123");
        return employeeValidation;
    }

}
