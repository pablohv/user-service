package org.company.user.infrastructure.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeJpaRepository extends JpaRepository<EmployeeEntity, UUID> {

    EmployeeEntity findByEmail(String email);

}
