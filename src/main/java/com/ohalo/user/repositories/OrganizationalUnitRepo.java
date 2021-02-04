package com.ohalo.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ohalo.user.models.OrganizationalUnit;

@Repository
public interface OrganizationalUnitRepo extends JpaRepository<OrganizationalUnit, Long> {

}
