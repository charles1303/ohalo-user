package com.ohalo.user.models;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.ohalo.user.utils.SecurityUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Employee extends AppUser {

	private String firstName;

	private String lastName;

	private LocalDate startDate;

	private LocalDate endDate;

	@Setter(AccessLevel.NONE)
	private String socSecNumber;

	@ManyToOne
	private OrganizationalUnit orgUnit;

	public void setSocSecNumber(String socSecNumber) {
		try {
			this.socSecNumber = SecurityUtils.hash(socSecNumber);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
