package org.company.user.factory.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeValidation;
import org.company.user.infrastructure.in.request.EmployeeValidateRequest;
import org.company.user.infrastructure.in.request.EmployerRequest;

public class ModelFactory {

    @SuppressFBWarnings(
            value = "HARD_CODE_PASSWORD",
            justification = "Password fake"
    )
    public static EmployerRequest createEmployerRq() {
        final EmployerRequest employee = new EmployerRequest();

        employee.setFirstName("Juan");
        employee.setLastName("Pérez");
        employee.setEmail("juan.perez1@email.com");
        employee.setPassword("Password1231");
        employee.setPhone("5512345678");

        employee.setState("Ciudad de México");
        employee.setCity("Benito Juárez");
        employee.setCp("03100");
        employee.setAddress("Av. Insurgentes Sur 123");

        employee.setCountry("México");
        employee.setNationality("Mexicana");

        return employee;
    }

    @SuppressFBWarnings(
            value = "HARD_CODE_PASSWORD",
            justification = "Password fake"
    )
    public static EmployeeValidateRequest createEmployeeValidateRq() {
        final EmployeeValidateRequest employee = new EmployeeValidateRequest();

        employee.setEmail("juan.perez@email.com");
        employee.setPassword("Password1232");

        return employee;
    }

    @SuppressFBWarnings(
            value = "HARD_CODE_PASSWORD",
            justification = "Password fake"
    )
    public static Employee createEmployee() {
        final Employee employee = new Employee();

        employee.setFirstName("Juan");
        employee.setLastName("Pérez");
        employee.setEmail("juan.perez2@email.com");
        employee.setPassword("Password1233");
        employee.setPhone("5512345678");

        employee.setState("Ciudad de México");
        employee.setCity("Benito Juárez");
        employee.setCp("03100");
        employee.setAddress("Av. Insurgentes Sur 123");

        employee.setCountry("México");
        employee.setNationality("Mexicana");

        return employee;
    }

    @SuppressFBWarnings(
            value = "HARD_CODE_PASSWORD",
            justification = "Password fake"
    )
    public static EmployeeValidation createEmployeeValidation() {
        final EmployeeValidation employeeValidation = new EmployeeValidation();

        employeeValidation.setEmail("juan.perez3@email.com");
        employeeValidation.setPassword("Password123");

        return employeeValidation;
    }

}
