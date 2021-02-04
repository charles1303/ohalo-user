package com.ohalo.user.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ohalo.user.models.AppUser;

public class AppUserRepoTest extends RepoTest {

	@Autowired
	private AppUserRepo appUserRepo;

	@Test
	public void shouldSaveAppUser() {
		AppUser auser = new AppUser();
		auser.setUsername("username");
		auser.setPassword("password");

		AppUser saved = appUserRepo.save(auser);

		Assertions.assertNotNull(auser.getId());
		Assertions.assertNotNull(saved.getId());
	}

}
