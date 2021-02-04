package com.ohalo.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ohalo.user.models.AppUser;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {

}
