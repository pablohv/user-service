package org.company.user.factorymodel;

import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeCredentials;
import org.company.user.infrastructure.in.request.EmployeeCredentialsRequest;
import org.company.user.infrastructure.in.request.EmployeeRequest;

public class ModelFactory {

    public static EmployeeRequest createEmployeeRq() {
        EmployeeRequest employee = new EmployeeRequest();

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

    public static EmployeeCredentialsRequest createEmployeeCredentialsRq() {
        EmployeeCredentialsRequest employee = new EmployeeCredentialsRequest();

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

    public static EmployeeCredentials createEmployeeCredentials() {
        EmployeeCredentials employeeCredentials = new EmployeeCredentials();
        employeeCredentials.setEmail("juan.perez@email.com");
        employeeCredentials.setPassword("Password123");
        return employeeCredentials;
    }

}
