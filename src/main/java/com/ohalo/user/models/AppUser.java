package com.ohalo.user.models;

import java.security.NoSuchAlgorithmException;
import java.util.Set;

//import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import com.ohalo.user.utils.SecurityUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public class AppUser extends BaseEntity {

	// @Column(unique = true)
	private String username;

	@Setter(AccessLevel.NONE)
	// @Column(nullable = false)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<UserRole> roles;

	public void setPassword(String password) {
		try {
			this.password = SecurityUtils.hash(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

}
