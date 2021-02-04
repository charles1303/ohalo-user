package com.ohalo.user.repositories;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;

import com.ohalo.user.models.Employee;

@TestInstance(Lifecycle.PER_CLASS)
public class EmployeeRepoTest extends RepoTest {

	@Autowired
	private EmployeeRepo employeeRepo;

	@BeforeAll
	void setUp() {

	}

	@Test
	public void shouldSaveEmployee() {

		Employee e = new Employee();
		e.setFirstName("firstname");
		e.setLastName("lastname");
		e.setStartDate(LocalDate.now());
		e.setEndDate(LocalDate.now());
		e.setSocSecNumber("xxxxxxxx");
		Employee saved = employeeRepo.save(e);

		Assertions.assertNotNull(e.getId());
		Assertions.assertNotNull(saved.getId());
		Assertions.assertNotEquals(saved.getSocSecNumber(), "xxxxxxxx");
		Assertions.assertNotEquals(e.getSocSecNumber(), "xxxxxxxx");
		Assertions.assertEquals(saved.getSocSecNumber(), e.getSocSecNumber());

	}
}
