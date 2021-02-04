package com.ohalo.user.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ohalo.user.models.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	@Query(value = "SELECT e FROM Employee e ORDER BY id")
	Page<Employee> findAllEmployeesWithPagination(Pageable pageable);

	@Query(value = "SELECT e FROM Employee e where e.orgUnit.id = :orgUnitId ORDER BY id")
	Page<Employee> findAllEmployeesWithPaginationByOrgUnit(Pageable pageable, @Param("orgUnitId") Long orgUnitId);
}
