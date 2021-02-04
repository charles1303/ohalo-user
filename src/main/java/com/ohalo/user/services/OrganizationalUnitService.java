package com.ohalo.user.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohalo.user.models.OrganizationalUnit;
import com.ohalo.user.repositories.OrganizationalUnitRepo;

@Service
public class OrganizationalUnitService {

	@Autowired
	OrganizationalUnitRepo orgUnitRepo;

	public OrganizationalUnit getOrgUnitById(Long orgUnitId) throws NoSuchElementException {
		return orgUnitRepo.findById(orgUnitId).get();
	}

	public OrganizationalUnit saveOrgUnit(OrganizationalUnit unit) throws NoSuchElementException {
		return orgUnitRepo.save(unit);
	}

}
