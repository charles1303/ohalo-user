package com.ohalo.user.services;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohalo.user.dto.EmployeeDto;
import com.ohalo.user.models.Employee;
import com.ohalo.user.repositories.EmployeeRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private OrganizationalUnitService orgUnitService;

	@Autowired
	private EmployeeLogService employeeLogService;

	@Transactional
	public EmployeeDto saveEmployee(EmployeeDto employeeDto)
			throws IllegalArgumentException, JsonProcessingException, NoSuchAlgorithmException {
		log.info("saveEmployee employeeDto.getId()==== {} ", employeeDto.getId());
		
		Employee emp = createEntity(employeeDto);
		employeeRepo.save(emp);
		employeeLogService.saveEmployeeLog(employeeDto);
		log.info("saved with id==== {}", emp.getId());
		employeeDto.setId(emp.getId());
		return employeeDto;

	}

	public List<Employee> getEmployees() {
		return employeeRepo.findAll();

	}

	public Employee findEmployeeById(Long employeeId) throws NoSuchElementException {

		log.info("retrieved by id==== {} ", employeeId);
		return employeeRepo.findById(employeeId).get();
	}

	@Transactional
	public EmployeeDto updateEmployee(EmployeeDto employeeDto, Long employeeId)
			throws NoSuchElementException, IllegalArgumentException, JsonProcessingException, NoSuchAlgorithmException {

		employeeDto.setId(employeeId);
		Employee emp = createEntity(employeeDto);
		employeeRepo.save(emp);
		log.info("update by id==== {} ", employeeId);
		employeeLogService.saveEmployeeLog(employeeDto);
		employeeDto.setId(emp.getId());
		return employeeDto;

	}

	@Transactional
	public Long disableEmployee(Long employeeId) throws NoSuchElementException, IllegalArgumentException {

		employeeRepo.deleteById(employeeId);
		log.info("delete by id==== {} ", employeeId);
		return employeeId;

	}

	public Page<Employee> findAllEmployeesWithPagination(Pageable pageable) {
		return employeeRepo.findAllEmployeesWithPagination(pageable);
	}

	public Page<Employee> findAllEmployeesWithPaginationByOrgUnit(Pageable pageable, Long orgUnitId) {
		return employeeRepo.findAllEmployeesWithPaginationByOrgUnit(pageable, orgUnitId);
	}

	private Employee createEntity(EmployeeDto employeeDto) throws NoSuchElementException {

		Employee emp = null;
		if (employeeDto.getId() != null) {
			emp = findEmployeeById(employeeDto.getId());
		} else {
			emp = new Employee();
			emp.setSocSecNumber(employeeDto.getSocSecNumber());
		}

		if (employeeDto.getOrgUnitId() != null) {
			emp.setOrgUnit(orgUnitService.getOrgUnitById(employeeDto.getOrgUnitId()));
		}

		log.info("employeeDto employeeDto.getId()==== {} ", employeeDto.getId());
		
		emp.setFirstName(employeeDto.getFirstName());
		emp.setLastName(employeeDto.getLastName());
		emp.setStartDate(employeeDto.getStartDate());
		emp.setEndDate(employeeDto.getEndDate());
		return emp;
	}

	public EmployeeRepo getEmployeeRepo() {
		return employeeRepo;
	}

	public void setEmployeeRepo(EmployeeRepo employeeRepo) {
		this.employeeRepo = employeeRepo;
	}

}
