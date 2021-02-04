package com.ohalo.user.models;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserRole extends BaseEntity {

	private String name;

	private String description;

}
