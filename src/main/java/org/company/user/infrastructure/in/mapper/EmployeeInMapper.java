package org.company.user.infrastructure.in.mapper;

import org.company.user.domain.model.Employee;
import org.company.user.domain.model.EmployeeCredentials;
import org.company.user.infrastructure.in.request.EmployeeCredentialsRequest;
import org.company.user.infrastructure.in.request.EmployerRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeInMapper {

    Employee toDomain(EmployerRequest employeeRequest);

    EmployeeCredentials toDomain(EmployeeCredentialsRequest employeeCredentialsRequest);

}
