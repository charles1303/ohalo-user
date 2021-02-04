package com.ohalo.user.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class EmployeeLog extends BaseEntity {

	private String data;

	private Long userId;

	private LocalDate entryDate = LocalDate.now();

}
