package org.company.user.infrastructure.in.mapper;

import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeCredentials;
import org.company.user.infrastructure.in.request.EmployeeCredentialsRequest;
import org.company.user.infrastructure.in.request.EmployeeRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeInMapper {

    Employee toDomain(EmployeeRequest employeeRequest);

    EmployeeCredentials toDomain(EmployeeCredentialsRequest employeeCredentialsRequest);

}
