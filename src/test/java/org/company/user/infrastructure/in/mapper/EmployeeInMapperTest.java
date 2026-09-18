package org.company.user.infrastructure.in.mapper;

import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeCredentials;
import org.company.user.infrastructure.in.request.EmployeeCredentialsRequest;
import org.company.user.infrastructure.in.request.EmployeeRequest;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeInMapperTest {

    private final EmployeeInMapper employeeInMapper = Mappers.getMapper(EmployeeInMapper.class);

    @Test
    void toDomainMapsAllFieldsFromEmployeeRequest() {
        final EmployeeRequest request = EmployeeRequest.builder()
                .firstName("Juan")
                .lastName("Pérez")
                .email("juan.perez@email.com")
                .password("Password123")
                .phone("5512345678")
                .state("Ciudad de México")
                .city("Benito Juárez")
                .cp("03100")
                .address("Av. Insurgentes Sur 123")
                .country("México")
                .nationality("Mexicana")
                .build();

        final Employee employee = employeeInMapper.toDomain(request);

        assertEquals(request.getFirstName(), employee.getFirstName());
        assertEquals(request.getLastName(), employee.getLastName());
        assertEquals(request.getEmail(), employee.getEmail());
        assertEquals(request.getPassword(), employee.getPassword());
        assertEquals(request.getPhone(), employee.getPhone());
        assertEquals(request.getState(), employee.getState());
        assertEquals(request.getCity(), employee.getCity());
        assertEquals(request.getCp(), employee.getCp());
        assertEquals(request.getAddress(), employee.getAddress());
        assertEquals(request.getCountry(), employee.getCountry());
        assertEquals(request.getNationality(), employee.getNationality());
    }

    @Test
    void toDomainMapsAllFieldsFromEmployeeCredentialsRequest() {
        final EmployeeCredentialsRequest request = EmployeeCredentialsRequest.builder()
                .email("juan.perez@email.com")
                .password("Password123")
                .build();

        final EmployeeCredentials credentials = employeeInMapper.toDomain(request);

        assertEquals(request.getEmail(), credentials.getEmail());
        assertEquals(request.getPassword(), credentials.getPassword());
    }

}
