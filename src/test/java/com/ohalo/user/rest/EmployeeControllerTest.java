package com.ohalo.user.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohalo.user.dto.EmployeeDto;
import com.ohalo.user.models.OrganizationalUnit;
import com.ohalo.user.services.EmployeeService;
import com.ohalo.user.services.OrganizationalUnitService;

@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmployeeControllerTest extends RestApiTest {

	@Autowired
	EmployeeService employeeService;

	@Autowired
	OrganizationalUnitService orgUnitService;

	@Test
	public void shouldReturnInvalidMessageWhenSaveEmployee() throws Exception {
		EmployeeDto employeeDto = createDto();
		employeeDto.setFirstName(null);
		String request = asJsonString(employeeDto);
		mvc.perform(MockMvcRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON).content(request))
				.andExpect(status().is4xxClientError()).andExpect(jsonPath("$.status", is("error")))
				.andExpect(jsonPath("$.data", is("Firstname is mandatory")));
	}

	@Test
	void shouldSaveEmployee() throws Exception {
		EmployeeDto employeeDto = createDto();
		String request = asJsonString(employeeDto);
		mvc.perform(MockMvcRequestBuilders.post("/employees").contentType(MediaType.APPLICATION_JSON).content(request))
				.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.status", is("success")));

	}

	@Test
	void shouldReturnNotFoundWhenFetchEmployeeById() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get("/employees/{id}", 14)).andExpect(status().is4xxClientError())
				.andExpect(jsonPath("$.status", is("error")));

	}

	@Test
	void shouldReturnWhenFetchEmployeeById() throws Exception {
		EmployeeDto employeeDto = saveEmployee();
		mvc.perform(MockMvcRequestBuilders.get("/employees/{id}", employeeDto.getId()))
				.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.status", is("success")));

	}

	@Test
	void shouldUpdateEmployeeById() throws Exception {

		EmployeeDto employeeDto = saveEmployee();

		EmployeeDto newEmployeeDto = new EmployeeDto();
		newEmployeeDto.setFirstName("firstname1");
		newEmployeeDto.setLastName("lastname");
		newEmployeeDto.setStartDate(LocalDate.now());
		newEmployeeDto.setEndDate(LocalDate.now());
		newEmployeeDto.setSocSecNumber("xxxxxxxx");

		mvc.perform(MockMvcRequestBuilders.put("/employees/{id}", employeeDto.getId())
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(newEmployeeDto)))
				.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.status", is("success")));

	}

	@Test
	void shouldDeleteEmployeeById() throws Exception {

		EmployeeDto employeeDto = saveEmployee();

		mvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", employeeDto.getId()))
				.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.status", is("success")));

	}

	@Test
	void shouldReturnEmployees() throws Exception {
		EmployeeDto employeeDto = saveEmployee();
		mvc.perform(MockMvcRequestBuilders.get("/employees")).andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.status", is("success")))
				.andExpect(jsonPath("$.data[0].firstName", is(employeeDto.getFirstName())));

	}

	@Test
	void shouldReturnPaginatedEmployees() throws Exception {
		EmployeeDto employeeDto = saveEmployee();
		mvc.perform(MockMvcRequestBuilders.get("/employees/paged?page=0&size=2")).andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$.status", is("success")))
				.andExpect(jsonPath("$.data.content[0].firstName", is(employeeDto.getFirstName())));

	}

	@Test
	void shouldReturnPaginatedEmployeesByOrgUnit() throws Exception {
		OrganizationalUnit unit = createOrgUnit();
		EmployeeDto employeeDto = createDto();
		employeeDto.setOrgUnitId(unit.getId());
		saveEmployee(employeeDto);
		mvc.perform(MockMvcRequestBuilders.get("/employees/paged-by-org-unit/{orgUnitId}?page=0&size=2", unit.getId()))
				.andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.status", is("success")))
				.andExpect(jsonPath("$.data.content[0].orgUnit.name", is(unit.getName())));

	}

	private EmployeeDto saveEmployee()
			throws IllegalArgumentException, JsonProcessingException, NoSuchAlgorithmException {
		EmployeeDto employeeDto = createDto();
		return employeeService.saveEmployee(employeeDto);
	}

	private EmployeeDto saveEmployee(EmployeeDto employeeDto)
			throws IllegalArgumentException, JsonProcessingException, NoSuchAlgorithmException {
		return employeeService.saveEmployee(employeeDto);
	}

	private EmployeeDto createDto() {
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setFirstName("firstname");
		employeeDto.setLastName("lastname");
		employeeDto.setStartDate(LocalDate.now());
		employeeDto.setEndDate(LocalDate.now());
		employeeDto.setSocSecNumber("xxxxxxxx");
		return employeeDto;
	}

	private OrganizationalUnit createOrgUnit() {
		OrganizationalUnit unit = new OrganizationalUnit();
		unit.setName("OrgUnit One");
		return orgUnitService.saveOrgUnit(unit);
	}
}
