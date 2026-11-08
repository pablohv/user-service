package org.company.user.infrastructure.out.repository.mapper;

import org.company.user.domain.model.Employee;
import org.company.user.infrastructure.out.repository.entity.EmployeeEntity;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface EmployeeEntityMapper {

    EmployeeEntity employeeToEntity(Employee employee);

    Employee toDomain(EmployeeEntity entity);

    default Optional<Employee> entityToEmploy(final EmployeeEntity entity) {
        if (entity == null) {
            return Optional.empty();
        }

        return Optional.of(toDomain(entity));
    }

}
