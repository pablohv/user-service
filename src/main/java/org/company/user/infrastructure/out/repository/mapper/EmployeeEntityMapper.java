package org.company.user.infrastructure.out.repository.mapper;

import org.company.user.domain.model.Employee;
import org.company.user.infrastructure.out.repository.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeEntityMapper {

    EmployeeEntity employeeToEntity(Employee employee);

    Employee entityToEmployee(EmployeeEntity entity);

}
