package org.company.user.factory.model;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeCredentials;
import org.company.user.infrastructure.in.request.EmployeeCredentialsRequest;
import org.company.user.infrastructure.in.request.EmployerRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
    public static EmployeeCredentialsRequest createEmployeeValidateRq() {
        final EmployeeCredentialsRequest employee = new EmployeeCredentialsRequest();

        employee.setEmail("juan.perez@email.com");
        employee.setPassword("Password1232");

        return employee;
    }

    public static Employee createEmployee() {
        return new Employee(
                "Juan",
                "Pérez",
                "juan.perez2@email.com",
                "Password1233",
                "5512345678",
                "Ciudad de México",
                "Benito Juárez",
                "03100",
                "Av. Insurgentes Sur 123",
                "México",
                "Mexicana"
        );
    }

    public static EmployeeCredentials createEmployeeValidation() {
        return new EmployeeCredentials(
                "juan.perez3@email.com",
                "Password123"
        );
    }

}
