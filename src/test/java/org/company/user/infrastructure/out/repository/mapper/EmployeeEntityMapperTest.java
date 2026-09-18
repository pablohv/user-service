package org.company.user.infrastructure.out.repository.mapper;

import org.company.user.domain.model.Employee;
import org.company.user.infrastructure.out.repository.entity.EmployeeEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.company.user.factorymodel.ModelFactory.createEmployee;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeEntityMapperTest {

    private final EmployeeEntityMapper employeeEntityMapper = Mappers.getMapper(EmployeeEntityMapper.class);

    @Test
    void employeeToEntityMapsAllFields() {
        final Employee employee = createEmployee();

        final EmployeeEntity entity = employeeEntityMapper.employeeToEntity(employee);

        assertEquals(employee.getFirstName(), entity.getFirstName());
        assertEquals(employee.getLastName(), entity.getLastName());
        assertEquals(employee.getEmail(), entity.getEmail());
        assertEquals(employee.getPassword(), entity.getPassword());
        assertEquals(employee.getPhone(), entity.getPhone());
        assertEquals(employee.getState(), entity.getState());
        assertEquals(employee.getCity(), entity.getCity());
        assertEquals(employee.getCp(), entity.getCp());
        assertEquals(employee.getAddress(), entity.getAddress());
        assertEquals(employee.getCountry(), entity.getCountry());
        assertEquals(employee.getNationality(), entity.getNationality());
    }

    @Test
    void entityToEmployeeMapsAllFields() {
        final EmployeeEntity entity = employeeEntityMapper.employeeToEntity(createEmployee());

        final Employee employee = employeeEntityMapper.entityToEmployee(entity);

        assertEquals(entity.getFirstName(), employee.getFirstName());
        assertEquals(entity.getLastName(), employee.getLastName());
        assertEquals(entity.getEmail(), employee.getEmail());
        assertEquals(entity.getPassword(), employee.getPassword());
        assertEquals(entity.getPhone(), employee.getPhone());
        assertEquals(entity.getState(), employee.getState());
        assertEquals(entity.getCity(), employee.getCity());
        assertEquals(entity.getCp(), employee.getCp());
        assertEquals(entity.getAddress(), employee.getAddress());
        assertEquals(entity.getCountry(), employee.getCountry());
        assertEquals(entity.getNationality(), employee.getNationality());
    }

}
