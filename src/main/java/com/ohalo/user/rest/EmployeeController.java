package com.ohalo.user.rest;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohalo.user.dto.EmployeeDto;
import com.ohalo.user.models.Employee;
import com.ohalo.user.rest.responses.AppFailureResponse;
import com.ohalo.user.rest.responses.AppResponse;
import com.ohalo.user.services.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping({ "employees" })
@Slf4j
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping
	public AppResponse<EmployeeDto> createEmployee(@Valid @RequestBody EmployeeDto employeeDto)
			throws IllegalArgumentException, JsonProcessingException, NoSuchAlgorithmException {
		log.info("createEmployee employeeDto.getId()==== {}", employeeDto.getId());
		log.info("createEmployee employeeDto.getSocSecNumber()==== {} ", employeeDto.getSocSecNumber());

		return getResponse(employeeService.saveEmployee(employeeDto));
	}

	@PutMapping("{id}")
	public AppResponse<EmployeeDto> updateEmployee(@Valid @RequestBody EmployeeDto employeeDto)
			throws IllegalArgumentException, JsonProcessingException, NoSuchAlgorithmException {
		return getResponse(employeeService.saveEmployee(employeeDto));
	}

	@DeleteMapping("{id}")
	public AppResponse<Long> removeEmployee(@PathVariable Long id) {
		return getResponse(employeeService.disableEmployee(id));
	}

	@GetMapping
	public AppResponse<List<Employee>> findEmployees() {
		return getResponse(employeeService.getEmployees());
	}

	@GetMapping("paged")
	public AppResponse<Page<Employee>> findPaginatedEmployees(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		log.info("paginated with page === {} and size === {} " + page, size);
		Pageable paging = PageRequest.of(page, size);
		return getResponse(employeeService.findAllEmployeesWithPagination(paging));
	}

	@GetMapping("paged-by-org-unit/{orgUnitId}")
	public AppResponse<Page<Employee>> findPaginatedEmployeesByOrgUnit(@PathVariable Long orgUnitId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		log.info("paginated with page === {} and size === {} and orgUnitId === {}", page, size, orgUnitId);
		Pageable paging = PageRequest.of(page, size);
		return getResponse(employeeService.findAllEmployeesWithPaginationByOrgUnit(paging, orgUnitId));
	}

	@GetMapping("{id}")
	public AppResponse<Employee> getEmployeeById(@PathVariable Long id) {

		return getResponse(employeeService.findEmployeeById(id));
	}

	public <T> AppResponse<T> getResponse(T data) {

		if (data == null) {
			return getFailResponse(data);
		}
		return new AppResponse<T>("success", data);
	}

	public <T> AppResponse<T> getFailResponse(T data) {
		return new AppResponse<>("fail", data);
	}

	public AppFailureResponse getErrorResponse(Object data, String message) {
		return new AppFailureResponse(message, data);
	}
}
