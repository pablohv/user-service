package org.company.user.infrastructure.out.repository.mapper;

import org.company.user.domain.model.Employee;
import org.company.user.infrastructure.out.repository.EmployeeEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeeEntityMapper {

    public EmployeeEntity employeeToEntity(Employee employee) {
        return EmployeeEntity.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .password(employee.getPassword())
                .phone(employee.getPhone())
                .state(employee.getState())
                .city(employee.getCity())
                .cp(employee.getCp())
                .address(employee.getAddress())
                .country(employee.getCountry())
                .nationality(employee.getNationality())
                .build();
    }

    public Employee entityToEmploy(EmployeeEntity entity) {
        Employee employee = new Employee();

        employee.setFirstName(entity.getFirstName());
        employee.setLastName(entity.getLastName());
        employee.setEmail(entity.getEmail());
        employee.setPassword(entity.getPassword());
        employee.setPhone(entity.getPhone());
        employee.setState(entity.getState());
        employee.setCity(entity.getCity());
        employee.setCp(entity.getCp());
        employee.setAddress(entity.getAddress());
        employee.setCountry(entity.getCountry());
        employee.setNationality(entity.getNationality());

        return employee;
    }

}
