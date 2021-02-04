package com.ohalo.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ohalo.user.models.EmployeeLog;

@Repository
public interface EmployeeLogRepo extends JpaRepository<EmployeeLog, Long> {

}
